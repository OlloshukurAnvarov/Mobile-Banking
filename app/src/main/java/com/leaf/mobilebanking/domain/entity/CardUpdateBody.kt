package com.leaf.mobilebanking.domain.entity


import com.google.gson.annotations.SerializedName

data class CardUpdateBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("theme")
    val theme: Int
)