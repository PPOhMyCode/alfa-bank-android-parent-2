package com.example.alfa_bank_android_app_parent_2.data

import android.content.Context
import android.content.SharedPreferences
import com.example.alfa_bank_android_app_parent_2.domain.Preferences


class PreferencesImpl(context: Context) : Preferences(context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_AUTHORIZATION, Context.MODE_PRIVATE)

    override var userPinCode: String?
        get() = preferences.getString(USER_PIN_CODE,"")
        set(value) = preferences.edit().putString(USER_PIN_CODE,value).apply ()

    override var isUserLogged: Boolean
        get() = preferences.getBoolean(IS_USER_LOGGED, false)
        set(value) = preferences.edit().putBoolean(IS_USER_LOGGED, value).apply()

    companion object {
        const val IS_USER_LOGGED = "IS_USER_LOGGED"
        const val SHARED_PREFERENCES_AUTHORIZATION = "SHARED_PREFERENCES_AUTHORIZATION"
        const val USER_PIN_CODE = "USER_PIN_CODE"
    }
}