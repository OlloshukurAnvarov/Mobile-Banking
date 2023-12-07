package com.leaf.mobilebanking.data.api

import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify
import com.leaf.mobilebanking.domain.entity.TransferBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransferAPI {
    @POST("transfers")
    suspend fun transferTo(
        @Header("Authorization") bearerToken: String,
        @Body transferBody: TransferBody
    ) : Verify

    @POST("transfers/verify")
    suspend fun transferVerify(
        @Header("Authorization") bearerToken: String,
        @Body verify: Verify
    ) : Any?

    @POST("transfers/resend")
    suspend fun transferVerifyResend(
        @Header("Authorization") bearerToken: String,
        @Body token: Token
    ) : Verify

}