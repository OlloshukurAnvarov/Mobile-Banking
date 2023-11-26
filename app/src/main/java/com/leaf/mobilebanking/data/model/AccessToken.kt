package com.leaf.mobilebanking.data.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("acccess_token")
    val token: String
)
