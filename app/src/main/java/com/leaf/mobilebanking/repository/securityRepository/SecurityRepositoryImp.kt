package com.leaf.mobilebanking.repository.securityRepository

import com.leaf.mobilebanking.data.dao.BankingDao
import com.leaf.mobilebanking.data.model.Password
import javax.inject.Inject

class SecurityRepositoryImp @Inject constructor(private val database: BankingDao) : SecurityRepository {
    override suspend fun savePassword(password: String) = database.savePassword(password)
    override suspend fun password(): Password = database.password()
    override suspend fun deletePassword(password: Password) = database.deletePassword(password)
}