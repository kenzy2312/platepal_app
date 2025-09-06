package com.example.recipeapp.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val USERNAME = "username"
    }

    fun saveAuthToken(username: String) {
        prefs.edit().putString(USERNAME, username).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USERNAME, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}