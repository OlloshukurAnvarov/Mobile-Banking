package com.leaf.mobilebanking.repository.cardsRepository

import com.leaf.mobilebanking.domain.entity.AddCardBody
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.domain.entity.CardResponse
import com.leaf.mobilebanking.domain.entity.CardUpdateBody

interface CardRepository {
    suspend fun cards(bearerToken: String): CardResponse

    suspend fun addCard(bearerToken: String, addCardBody: AddCardBody): CardData

    suspend fun updateCard(
        bearerToken: String,
        cardId: String,
        cardUpdateBody: CardUpdateBody
    ): CardData

    suspend fun delete(bearerToken: String, cardId: String): String

}