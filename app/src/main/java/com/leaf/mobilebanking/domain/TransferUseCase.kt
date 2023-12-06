package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.AddCardErrorBody
import com.leaf.mobilebanking.data.model.TransfersErrorBody
import com.leaf.mobilebanking.data.preferences.Settings
import com.leaf.mobilebanking.domain.entity.TransferBody
import com.leaf.mobilebanking.repository.transferRepository.TransferRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TransferUseCase @Inject constructor(
    private val repository: TransferRepository,
    private val settings: Settings
) {
    suspend operator fun invoke(from: Int?, to: String, amount: String?): State {
        val bearerToken = settings.accessToken ?: return State.Error(-1)

        if (from == null || from < 0) return State.Error(ErrorCodes.CARD_ID_ERROR)
        if (amount == null || amount.toString().isEmpty()) return State.Error(ErrorCodes.CARD_AMOUNT_ERROR)
        if (to.length != 16) return State.Error(ErrorCodes.CARD_DIGITS_ERROR)

        try {
            val entity = TransferBody(
                fromCardId = from,
                pan = to,
                amount = amount.toInt()
            )
            val token = "Bearer $bearerToken"
            val response = repository.transferTo(token, entity)

            settings.apply {
                temporaryToken = response.token
                code = response.code
            }

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.string(), TransfersErrorBody::class.java)
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