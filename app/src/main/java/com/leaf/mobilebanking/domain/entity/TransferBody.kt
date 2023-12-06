package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class TransferBody(
    @SerializedName("from_card_id")
    val fromCardId: Int,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("amount")
    val amount: Int
)