package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class SignInBody(
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)