package com.leaf.mobilebanking.repository.signInRepository

import com.leaf.mobilebanking.data.model.AccessTokenSignIn
import com.leaf.mobilebanking.data.model.SignInBody

interface SignInRepository {
    suspend fun signIn(signInBody: SignInBody): AccessTokenSignIn
}