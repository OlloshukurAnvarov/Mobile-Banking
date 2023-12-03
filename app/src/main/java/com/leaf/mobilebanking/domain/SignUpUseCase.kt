package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.ErrorBody
import com.leaf.mobilebanking.data.model.SignUpBody
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.signUpRepository.SignUpRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignUpRepository, private val settings: Settings) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        phone: String
    ): State {

        if (firstName.isEmpty() || firstName.length < 3) return State.Error(ErrorCodes.FIRST_NAME_ERROR)

        if (lastName.isEmpty() || lastName.length < 3) return State.Error(ErrorCodes.LAST_NAME_ERROR)

        if (phone.isEmpty() || phone.length != 13) return State.Error(ErrorCodes.PHONE_NUMBER_ERROR)

        if (password.isEmpty() || password.length < 4) return State.Error(ErrorCodes.PASSWORD_ERROR)

        try {
            val entity = SignUpBody(firstName, lastName, password, phone)
            val response = repository.signUp(entity)

            settings.apply {
                temporaryToken = response.token
                code = response.code
                this.phone = phone
            }

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.charStream(), ErrorBody::class.java)
                        return State.ErrorIO(error.message)
                    } catch (ee: Exception) {
                        if (ee is IOException) return State.NoNetwork
                        return State.Error(-1)
                    }
                }
            }
            return State.Error(-1)
        }
        return State.Success<Unit>()
    }
}