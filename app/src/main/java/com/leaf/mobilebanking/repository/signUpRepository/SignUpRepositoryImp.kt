package com.leaf.mobilebanking.repository.signUpRepository

import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.model.SignUpBody
import com.leaf.mobilebanking.data.model.Verify
import javax.inject.Inject

class SignUpRepositoryImp @Inject constructor(private val authAPI: AuthAPI) : SignUpRepository {
    override suspend fun signUp(signUpBody: SignUpBody): Verify = authAPI.signUp(signUpBody)

}