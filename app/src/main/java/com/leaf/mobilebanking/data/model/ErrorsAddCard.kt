package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class ErrorsAddCard(
    @SerializedName("expire_month")
    val expireMonth: List<String>,
    @SerializedName("expire_year")
    val expireYear: List<String>,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("pan")
    val pan: List<String>
)