package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.SignInBody
import com.leaf.mobilebanking.data.model.SignInError
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.signInRepository.SignInRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInUseCache @Inject constructor(
    private val repository: SignInRepository,
    private val settings: Settings
) {
    suspend operator fun invoke(password: String, phone: String): State {
        if (phone.isEmpty() || phone.length != 13) return State.Error(ErrorCodes.PHONE_NUMBER_ERROR)

        if (password.isEmpty() || password.length < 4) return State.Error(ErrorCodes.PASSWORD_ERROR)

        try {
            val entity = SignInBody(password, phone)
            val response = repository.signIn(entity)

            settings.accessToken = response.token

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.charStream(), SignInError::class.java)
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