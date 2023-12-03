package com.leaf.mobilebanking.domain

import com.google.gson.GsonBuilder
import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.data.model.AddCardErrorBody
import com.leaf.mobilebanking.domain.entity.AddCardBody
import com.leaf.mobilebanking.repository.cardsRepository.CardRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardRepository: CardRepository) {
    suspend operator fun invoke(
        bearerToken: String?,
        expireMonth: Int,
        expireYear: Int,
        name: String,
        pan: String
    ): State {
        if (bearerToken == null) return State.Error(-1)
        if (!(expireMonth in 1..12 || expireYear in 2024..2050)) return State.Error(ErrorCodes.MONTH_YEAR_ERROR)
        if (name.length < 3) return State.Error(ErrorCodes.CARD_NAME_ERROR)
        if (pan.length != 16) return State.Error(ErrorCodes.CARD_DIGITS_ERROR)

        try {
            val entity = AddCardBody(
                expireMonth = expireMonth,
                expireYear = expireYear,
                name = name,
                pan = pan
            )
            val response = cardRepository.addCard(bearerToken, entity)

        }catch (e: Exception){
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()
                if (errorBody != null) {
                    try {
                        val error = GsonBuilder().create()
                            .fromJson(errorBody.charStream(), AddCardErrorBody::class.java)
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