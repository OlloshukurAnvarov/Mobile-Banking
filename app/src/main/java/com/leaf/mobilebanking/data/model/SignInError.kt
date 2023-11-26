package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class SignInError(
    @SerializedName("errors")
    val errors: ErrorSignIn,
    @SerializedName("message")
    val message: String
)