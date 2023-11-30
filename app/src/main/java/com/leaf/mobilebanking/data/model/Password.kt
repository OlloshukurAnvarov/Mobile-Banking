package com.leaf.mobilebanking.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    @PrimaryKey(true)
    val id: Int = 0,
    val password: String
)
