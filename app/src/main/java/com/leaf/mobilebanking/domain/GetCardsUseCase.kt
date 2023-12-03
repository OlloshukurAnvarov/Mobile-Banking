package com.leaf.mobilebanking.domain

import android.util.Log
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.repository.cardsRepository.CardRepository
import java.io.IOException
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val repository: CardRepository) {

    suspend operator fun invoke(bearerToken: String?): State {

        if (bearerToken == null) return State.Error(-1)
        val token = "Bearer $bearerToken"
        try {
            val entity = repository.cards(token)
            return State.Success(entity.data)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            return State.Error(-1)
        }
    }

}