package com.leaf.mobilebanking.data.api

import com.leaf.mobilebanking.domain.entity.AddCardBody
import com.leaf.mobilebanking.domain.entity.CardData
import com.leaf.mobilebanking.domain.entity.CardResponse
import com.leaf.mobilebanking.domain.entity.CardUpdateBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CardAPI {

    @GET("cards")
    suspend fun cards(@Header("Authorization") bearerToken: String): CardResponse

    @POST("cards")
    suspend fun addCard(
        @Header("Authorization") bearerToken: String,
        @Body addCardBody: AddCardBody
    ): CardData

    @PUT("cards/{cardId}")
    suspend fun updateCard(
        @Header("Authorization") bearerToken: String,
        @Path("cardId") cardId: String,
        @Body cardUpdateBody: CardUpdateBody
    ): CardData

    @DELETE("cards/{cardId}")
    suspend fun delete(
        @Header("Authorization") bearerToken: String,
        @Path("cardId") cardId: String
    ): String

}