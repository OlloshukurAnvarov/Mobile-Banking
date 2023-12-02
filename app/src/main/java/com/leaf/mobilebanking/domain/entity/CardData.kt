package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class CardData(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("expire_month")
    val expireMonth: Int,
    @SerializedName("expire_year")
    val expireYear: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("theme")
    val theme: Int
)