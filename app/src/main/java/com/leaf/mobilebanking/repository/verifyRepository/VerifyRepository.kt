package com.leaf.mobilebanking.repository.verifyRepository

import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify

interface VerifyRepository {
    suspend fun signUpVerify(verify: Verify): Token
}