package com.example.recipeapp.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val USERNAME = "username"
        private const val USER_ID = "user_id"
    }

    fun saveAuthToken(username: String) {
        prefs.edit().putString(USERNAME, username).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USERNAME, null)
    }

    fun saveUserId(userId: Int) {
        prefs.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    fun saveUserSession(userId: Int, username: String) {
        prefs.edit()
            .putInt(USER_ID, userId)
            .putString(USERNAME, username)
            .apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}