package com.leaf.mobilebanking.data.api

import com.leaf.mobilebanking.domain.entity.Card
import retrofit2.http.GET
import retrofit2.http.Header

interface CardAPI {

    @GET("cards")
    suspend fun cards(@Header("Authorization") bearerToken: String): Card<Any>

}