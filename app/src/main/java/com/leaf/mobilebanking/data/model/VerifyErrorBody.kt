package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class VerifyErrorBody(
    @SerializedName("errors")
    val errors: ErrorsVerify,
    @SerializedName("message")
    val message: String
)