package com.leaf.mobilebanking.data.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsImp @Inject constructor(@ApplicationContext context: Context) : Settings {
    private val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    override var accessToken: String?
        get() = preferences.getString("acc_token", null)
        set(value) = preferences.edit().putString("acc_token", value).apply()
    override var temporaryToken: String?
        get() = preferences.getString("temp_token", null)
        set(value) = preferences.edit().putString("temp_token", value).apply()
    override var code: String?
        get() = preferences.getString("code", null)
        set(value) = preferences.edit().putString("code", value).apply()
    override var cookies: Boolean
        get() = preferences.getBoolean("cookies", false)
        set(value) {
            preferences.edit().putBoolean("cookies", value).apply()
        }
}