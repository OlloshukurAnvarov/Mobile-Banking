package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class ErrorsX(
    @SerializedName("amount")
    val amount: List<String>,
    @SerializedName("from_card_id")
    val fromCardId: List<String>,
    @SerializedName("pan")
    val pan: List<String>
)