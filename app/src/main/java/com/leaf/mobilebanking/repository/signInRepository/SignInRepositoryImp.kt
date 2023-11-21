package com.leaf.mobilebanking.repository.signInRepository

import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.model.SignInBody
import com.leaf.mobilebanking.data.model.Token
import javax.inject.Inject

class SignInRepositoryImp @Inject constructor(private val authAPI: AuthAPI) : SignInRepository {
    override suspend fun signIn(signInBody: SignInBody): Token = authAPI.signIn(signInBody)

}