package com.g_one_hospitalapp.utilities

import android.content.Context
import com.g_one_hospitalapp.api.responses.LoginData
import com.g_one_hospitalapp.api.responses.UserResponse
import com.google.gson.Gson

class UserPreference(context: Context) {
    companion object {
        private const val PREFERENCE_NAME = "user_preference"
        private const val USER = "user"
        private const val ACCESS_TOKEN = "access_token"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val CHAT_ROOM_ID = "chat_room_id"
    }

    private val gson = Gson()
    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    // User login
    fun setLoginData(value: LoginData) {
        val editor = preferences.edit()
        editor.apply {
            putString(USER, gson.toJson(value.user))
            putString(ACCESS_TOKEN, value.accessToken)
            putBoolean(IS_LOGGED_IN, true)
        }.apply()
    }
    fun clearLoginData() {
        val editor = preferences.edit()
        editor.apply {
            putString(USER, "")
            putString(ACCESS_TOKEN, "")
            putBoolean(IS_LOGGED_IN, false)
        }.apply()
    }
    fun getLoginData(): LoginData {
        val json = gson.fromJson(preferences.getString(USER, ""), UserResponse::class.java)
        val accessToken = preferences.getString(ACCESS_TOKEN, "")
        return LoginData(user = json, accessToken = accessToken)
    }
    fun getIsLoggedIn(): Boolean = preferences.getBoolean(IS_LOGGED_IN, false)

    // Chats
    fun setChatRoomId(value: String) {
        val editor = preferences.edit()
        editor.apply { putString(CHAT_ROOM_ID, value) }.apply()
    }
    fun getChatRoomId(): String? = preferences.getString(CHAT_ROOM_ID, "")
}