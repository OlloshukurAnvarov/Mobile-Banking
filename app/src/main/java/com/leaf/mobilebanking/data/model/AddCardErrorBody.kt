package com.leaf.mobilebanking.data.model


import com.google.gson.annotations.SerializedName

data class AddCardErrorBody(
    @SerializedName("errors")
    val errors: ErrorsAddCard,
    @SerializedName("message")
    val message: String
)