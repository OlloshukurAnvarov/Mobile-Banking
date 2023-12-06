package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify
import com.leaf.mobilebanking.data.model.VerifyErrorBody
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.repository.confirmRepository.ConfirmRepository
import com.leaf.mobilebanking.repository.verifyRepository.VerifyRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConfirmUseCase @Inject constructor(
    private val repository: ConfirmRepository,
    private val settings: Settings
) {

    suspend operator fun invoke(token: String?, code: String): State {
        val bearerToken = settings.accessToken ?: return State.Error(-1)
        if (token.isNullOrEmpty() || code.isEmpty()) return State.Error(ErrorCodes.CODE_ERROR)

        try {
            val entity = Verify(token, code)
            val btoken = "Bearer $bearerToken"
            val response = repository.transferVerify(btoken, entity)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.charStream(), VerifyErrorBody::class.java)
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

    suspend fun resend() {
        val tempToken = settings.temporaryToken
        val btoken = "Bearer ${settings.accessToken}"
        if (tempToken.isNullOrEmpty()) return
        try {
            val entity = Token(tempToken)
            val response = repository.transferVerifyResend(btoken, entity)

            settings.apply {
                temporaryToken = response.token
                code = response.code
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}