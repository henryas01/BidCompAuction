//package utils

package com.example.bidcompauction.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.bidcompauction.data.model.LoginResponse
import com.example.bidcompauction.data.model.User
import com.google.gson.Gson

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val gson = Gson()

    companion object {
        private const val PREF_NAME = "meetbuddyAuthPrefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_USER_DATA = "user_data"
    }

    fun saveLogin(response: LoginResponse) {
        editor.putString(KEY_ACCESS_TOKEN, response.access_token)

        val userJson = gson.toJson(response.user)
        editor.putString(KEY_USER_DATA, userJson)

        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return !prefs.getString(KEY_ACCESS_TOKEN, null).isNullOrEmpty()
    }

    fun getUserData(): User? {
        val userJson = prefs.getString(KEY_USER_DATA, null)
        return gson.fromJson(userJson, User::class.java)
    }

    fun getToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun logout() {
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_USER_DATA)
        editor.apply()
    }
}