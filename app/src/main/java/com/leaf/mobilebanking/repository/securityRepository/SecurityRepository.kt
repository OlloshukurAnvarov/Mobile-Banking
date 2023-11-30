package com.leaf.mobilebanking.repository.securityRepository

import com.leaf.mobilebanking.data.model.Password

interface SecurityRepository {
    suspend fun savePassword(password: String)
    suspend fun password(): Password

    suspend fun deletePassword(password: Password)
}