package com.leaf.mobilebanking.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leaf.mobilebanking.data.model.Password

@Dao
interface BankingDao {
    @Query("INSERT INTO Password(password) VALUES(:password)")
    suspend fun savePassword(password: String)

    @Query("SELECT * FROM password")
    suspend fun password(): Password

    @Delete
    suspend fun deletePassword(password: Password)

}