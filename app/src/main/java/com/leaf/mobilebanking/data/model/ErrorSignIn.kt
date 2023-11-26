package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class ErrorSignIn(
    @SerializedName("password")
    val password: List<String>,
    @SerializedName("phone_number")
    val phoneNumber: List<String>
)