package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("data")
    val data: List<CardData>,
    @SerializedName("links")
    val links: Links,
    @SerializedName("meta")
    val meta: Meta
)