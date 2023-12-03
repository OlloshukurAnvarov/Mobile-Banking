package com.leaf.mobilebanking.repository.cardsRepository

import com.leaf.mobilebanking.data.api.CardAPI
import com.leaf.mobilebanking.domain.entity.AddCardBody
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.domain.entity.CardResponse
import com.leaf.mobilebanking.domain.entity.CardUpdateBody
import javax.inject.Inject

class CardRepositoryImp @Inject constructor(private val cardAPI: CardAPI) : CardRepository {
    override suspend fun cards(bearerToken: String): CardResponse = cardAPI.cards(bearerToken)

    override suspend fun addCard(bearerToken: String, addCardBody: AddCardBody): CardData =
        cardAPI.addCard(bearerToken, addCardBody)

    override suspend fun updateCard(
        bearerToken: String,
        cardId: String,
        cardUpdateBody: CardUpdateBody
    ): CardData = cardAPI.updateCard(bearerToken, cardId, cardUpdateBody)

    override suspend fun delete(bearerToken: String, cardId: String): String =
        cardAPI.delete(bearerToken, cardId)
}