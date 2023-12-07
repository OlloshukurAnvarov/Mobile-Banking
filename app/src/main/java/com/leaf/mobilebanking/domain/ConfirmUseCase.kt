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
        val btoken = "Bearer $bearerToken"

        try {
            val entity = Verify(token, code)
            repository.transferVerify(btoken, entity)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) return State.Error(ErrorCodes.CODE_ERROR)
            return State.Error(-1)
        }

        return State.Success<Unit>()

    }
}