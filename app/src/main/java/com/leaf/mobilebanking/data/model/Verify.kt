package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class Verify(
    @SerializedName("code")
    val code: String,
    @SerializedName("token")
    val token: String
)