package com.leaf.mobilebanking.data.preferences

import android.content.Context

class Settings(context: Context) {
    private val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    fun setToken(token: String?) = preferences.edit().putString("token", token).apply()
    fun getToken() = preferences.getString("token", null)
}