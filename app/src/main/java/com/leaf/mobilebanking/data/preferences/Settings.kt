package com.leaf.mobilebanking.data.preferences

interface Settings {
    var accessToken: String?
    var temporaryToken: String?
    var code: String?
    var cookies: Boolean
}