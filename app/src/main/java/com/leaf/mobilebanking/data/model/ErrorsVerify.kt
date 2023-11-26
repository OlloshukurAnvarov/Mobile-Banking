package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class ErrorsVerify(
    @SerializedName("code")
    val code: List<String>? = null,
    @SerializedName("token")
    val token: List<String>
)