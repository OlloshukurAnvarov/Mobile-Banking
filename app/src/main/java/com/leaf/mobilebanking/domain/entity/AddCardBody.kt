package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class AddCardBody(
    @SerializedName("expire_month")
    val expireMonth: Int,
    @SerializedName("expire_year")
    val expireYear: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pan")
    val pan: String
)