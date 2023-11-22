package com.leaf.mobilebanking.domain

import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.SignUpBody
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepository
import java.io.IOException
import javax.inject.Inject

class SignUpUseCache @Inject constructor(private val repository: SignUpRepository) {

    @Inject
    lateinit var settings: Settings
    suspend operator fun invoke(
        firstName: String?,
        lastName: String?,
        password: String?,
        phone: String?
    ): State {

        if (firstName.isNullOrEmpty() || firstName.length < 3) return State.Error(ErrorCodes.FIRST_NAME_ERROR)

        if (lastName.isNullOrEmpty() || lastName.length < 3) return State.Error(ErrorCodes.LAST_NAME_ERROR)

        if (phone.isNullOrEmpty() || phone.length != 13) return State.Error(ErrorCodes.PHONE_NUMBER_ERROR)

        if (password.isNullOrEmpty() || password.length < 4) return State.Error(ErrorCodes.PASSWORD_ERROR)

        try {
            val entity = SignUpBody(firstName, lastName, password, phone)
            val response = repository.signUp(entity)

            settings.temporaryToken = response.token
            settings.code = response.code

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            return State.Error(-1)
        }
        return State.Success<Unit>()
    }
}