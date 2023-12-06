package com.leaf.mobilebanking.repository.confirmRepository

import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify

interface ConfirmRepository {
    suspend fun transferVerify(bearerToken: String, verify: Verify): String

    suspend fun transferVerifyResend(
        bearerToken: String, token: Token
    ) : Verify

}