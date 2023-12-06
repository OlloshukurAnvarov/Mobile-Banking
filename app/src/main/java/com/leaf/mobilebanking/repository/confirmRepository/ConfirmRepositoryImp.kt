package com.leaf.mobilebanking.repository.confirmRepository

import com.leaf.mobilebanking.data.api.TransferAPI
import com.leaf.mobilebanking.data.model.Token
import com.leaf.mobilebanking.data.model.Verify
import javax.inject.Inject

class ConfirmRepositoryImp @Inject constructor(private val transferAPI: TransferAPI) : ConfirmRepository {
    override suspend fun transferVerify(bearerToken: String, verify: Verify): String = transferAPI.transferVerify(bearerToken, verify)
    override suspend fun transferVerifyResend(bearerToken: String, token: Token): Verify = transferAPI.transferVerifyResend(bearerToken, token)

}