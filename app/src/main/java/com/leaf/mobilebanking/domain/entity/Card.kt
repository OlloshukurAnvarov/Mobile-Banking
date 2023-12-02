package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class Card<T>(
    @SerializedName("data")
    val `data`: List<CardData>,
    @SerializedName("links")
    val links: T,
    @SerializedName("meta")
    val meta: T
)