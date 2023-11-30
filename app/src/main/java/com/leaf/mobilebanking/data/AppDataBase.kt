package com.leaf.mobilebanking.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leaf.mobilebanking.data.dao.BankingDao
import com.leaf.mobilebanking.data.model.Password

@Database(entities = [Password::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bankingDao() : BankingDao
}