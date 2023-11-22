package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class Verify(
    @SerializedName("token")
    val token: String,
    @SerializedName("code")
    val code: String
)