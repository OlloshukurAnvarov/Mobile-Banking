package com.leaf.mobilebanking.repository.verifyRepository

import com.leaf.mobilebanking.data.model.AccessToken
import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify

interface VerifyRepository {
    suspend fun signUpVerify(verify: Verify): AccessToken

    suspend fun resendSMS(token: Token): Verify

}