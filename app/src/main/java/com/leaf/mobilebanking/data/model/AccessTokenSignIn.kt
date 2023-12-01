package com.leaf.mobilebanking.data.model

import com.google.gson.annotations.SerializedName

data class AccessTokenSignIn(
    @SerializedName("access_token")
    val token: String
)
