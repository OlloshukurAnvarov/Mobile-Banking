package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("first_name")
    var firstName: List<String>? = null,
    @SerializedName("last_name")
    var lastName: List<String>? = null,
    @SerializedName("password")
    var password: List<String>? = null,
    @SerializedName("phone_number")
    var phoneNumber: List<String>? = null
)