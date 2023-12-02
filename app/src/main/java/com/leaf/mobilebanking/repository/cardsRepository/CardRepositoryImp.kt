package com.leaf.mobilebanking.repository.cardsRepository

import com.leaf.mobilebanking.data.api.CardAPI
import com.leaf.mobilebanking.domain.entity.Card
import javax.inject.Inject

class CardRepositoryImp @Inject constructor(private val cardAPI: CardAPI) : CardRepository {
    override suspend fun cards(bearerToken: String): Card<Any> = cardAPI.cards(bearerToken)
}