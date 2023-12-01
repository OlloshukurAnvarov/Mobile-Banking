package com.leaf.mobilebanking.repository.signInRepository

import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.model.AccessTokenSignIn
import com.leaf.mobilebanking.data.model.AccessTokenSignUp
import com.leaf.mobilebanking.data.model.SignInBody
import javax.inject.Inject

class SignInRepositoryImp @Inject constructor(private val authAPI: AuthAPI) : SignInRepository {
    override suspend fun signIn(signInBody: SignInBody): AccessTokenSignIn =
        authAPI.signIn(signInBody)

}