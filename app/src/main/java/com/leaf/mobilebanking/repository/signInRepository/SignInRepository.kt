package com.leaf.mobilebanking.repository.signInRepository

import com.leaf.mobilebanking.data.model.AccessToken
import com.leaf.mobilebanking.data.model.SignInBody
import com.leaf.mobilebanking.data.model.Token

interface SignInRepository {
    suspend fun signIn(signInBody: SignInBody): AccessToken
}