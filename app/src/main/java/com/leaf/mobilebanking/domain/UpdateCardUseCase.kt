package com.leaf.mobilebanking.domain

import com.leaf.mobilebanking.data.constants.ErrorCodes
import com.leaf.mobilebanking.data.constants.State
import com.leaf.mobilebanking.domain.entity.CardUpdateBody
import com.leaf.mobilebanking.repository.cardsRepository.CardRepository
import java.io.IOException
import javax.inject.Inject

class UpdateCardUseCase @Inject constructor(private val repository: CardRepository) {
    suspend operator fun invoke(bearerToken: String?, id: Int, name: String) : State {
        if (bearerToken == null) return State.Error(-1)
        if (name.length < 3) return State.Error(ErrorCodes.CARD_NAME_ERROR)

        try {
            val entity = CardUpdateBody(name, 1)
            val token = "Bearer $bearerToken"
            repository.updateCard(token, id.toString(), entity)
        }catch (e: Exception){
            e.printStackTrace()
            if (e is IOException) return State.NoNetwork
            State.Error(-1)
        }
        return State.Success<Unit>()
    }
}