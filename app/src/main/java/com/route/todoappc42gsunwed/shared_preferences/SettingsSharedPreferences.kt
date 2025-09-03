package com.route.todoappc42gsunwed.shared_preferences

import android.content.Context
import android.content.SharedPreferences

class SettingsSharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getLanguage(): String {
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun setLanguage(language: String) {
        editor.putString("language", language).apply()
    }

    fun getMode(): String {
        return sharedPreferences.getString("mode", "light") ?: "light"
    }

    fun setMode(mode: String) {
        editor.putString("mode", mode).apply()
    }
}
