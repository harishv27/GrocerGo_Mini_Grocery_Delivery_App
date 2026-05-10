package com.example.grocery_app.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("grocer_go_prefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun setMobile(mobile: String) {
        prefs.edit().putString(KEY_MOBILE, mobile).apply()
    }

    fun getMobile(): String? = prefs.getString(KEY_MOBILE, null)

    fun logout() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_MOBILE = "mobile"
    }
}
