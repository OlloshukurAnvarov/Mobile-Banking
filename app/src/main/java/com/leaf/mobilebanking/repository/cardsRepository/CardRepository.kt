package com.leaf.mobilebanking.repository.cardsRepository

import com.leaf.mobilebanking.domain.entity.Card

interface CardRepository {
    suspend fun cards(bearerToken: String): Card<Any>
}