package com.leaf.mobilebanking.repository.cardsRepository

import com.leaf.mobilebanking.domain.entity.CardResponse

interface CardRepository {
    suspend fun cards(bearerToken: String): CardResponse
}