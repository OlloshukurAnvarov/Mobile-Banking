package com.leaf.mobilebanking.data.api

import com.leaf.mobilebanking.data.model.AccessTokenSignIn
import com.leaf.mobilebanking.data.model.AccessTokenSignUp
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
    suspend fun signIn(@Body signInBody: SignInBody): AccessTokenSignIn

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body verify: Verify): AccessTokenSignUp

    @POST("auth/sign-up/resend")
    suspend fun resendSMS(@Body token: Token): Verify

}