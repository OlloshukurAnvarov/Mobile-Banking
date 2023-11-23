package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("errors")
    var errors: Errors?,
    @SerializedName("message")
    val message: String
)