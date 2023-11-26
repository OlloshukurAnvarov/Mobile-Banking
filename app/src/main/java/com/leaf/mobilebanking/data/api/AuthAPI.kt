package com.leaf.mobilebanking.data.api

import com.leaf.mobilebanking.data.model.AccessToken
import com.leaf.mobilebanking.data.model.SignInBody
import com.leaf.mobilebanking.data.model.SignUpBody
import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpBody: SignUpBody): Verify

    @POST("auth/sign-in")
    suspend fun signIn(@Body signInBody: SignInBody): AccessToken

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body verify: Verify): AccessToken

    @POST("auth/sign-up/resend")
    suspend fun resendSMS(@Body token: Token): Verify

}