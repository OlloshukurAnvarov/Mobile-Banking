package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class TransfersErrorBody(
    @SerializedName("errors")
    val errors: ErrorsX,
    @SerializedName("message")
    val message: String
)