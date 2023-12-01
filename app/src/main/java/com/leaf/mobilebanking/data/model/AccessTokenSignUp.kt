package com.leaf.mobilebanking.data.model

import com.google.gson.annotations.SerializedName

data class AccessTokenSignUp(
    @SerializedName("acccess_token")
    val token: String
)
