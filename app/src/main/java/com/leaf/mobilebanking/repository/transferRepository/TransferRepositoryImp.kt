package com.leaf.mobilebanking.repository.transferRepository

import com.leaf.mobilebanking.data.api.TransferAPI
import com.leaf.mobilebanking.data.model.Verify
import com.leaf.mobilebanking.domain.entity.TransferBody
import javax.inject.Inject

class TransferRepositoryImp @Inject constructor(private val transferAPI: TransferAPI) : TransferRepository {
    override suspend fun transferTo(bearerToken: String, transferBody: TransferBody): Verify = transferAPI.transferTo(bearerToken, transferBody)

    override suspend fun transferVerify(bearerToken: String, verify: Verify): String = transferAPI.transferVerify(bearerToken, verify)
}