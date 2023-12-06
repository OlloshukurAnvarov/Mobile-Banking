package com.leaf.mobilebanking.repository.transferRepository

import com.leaf.mobilebanking.data.model.Verify
import com.leaf.mobilebanking.domain.entity.TransferBody

interface TransferRepository {
    suspend fun transferTo(bearerToken: String, transferBody: TransferBody): Verify
    suspend fun transferVerify(bearerToken: String, verify: Verify): String
}