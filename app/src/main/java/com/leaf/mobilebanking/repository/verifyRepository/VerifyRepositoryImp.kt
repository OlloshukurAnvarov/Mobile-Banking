package com.leaf.mobilebanking.repository.verifyRepository

import com.leaf.mobilebanking.data.api.AuthAPI
import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify
import javax.inject.Inject

class VerifyRepositoryImp @Inject constructor(private val authAPI: AuthAPI) : VerifyRepository {
    override suspend fun signUpVerify(verify: Verify): Token = authAPI.signUpVerify(verify)
}