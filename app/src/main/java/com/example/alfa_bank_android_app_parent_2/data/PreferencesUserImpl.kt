package com.example.alfa_bank_android_app_parent_2.data

import android.content.Context
import android.content.SharedPreferences
import com.example.alfa_bank_android_app_parent_2.domain.PreferencesUser
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child



class PreferencesUserImpl(context: Context) : PreferencesUser() {
    private val preferencesAuthorization: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_AUTHORIZATION, Context.MODE_PRIVATE)

    private val preferencesChild: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_CHILD, Context.MODE_PRIVATE)

    private var isChildWasAdded: Boolean = false

    override var userChild: Child?
        get() = getChild()
        set(value) {
            isChildWasAdded = false
            value?.let { child ->
                isChildWasAdded = true
                with(preferencesChild.edit()) {
                    putString(NAME_CHILD, child.name).apply()
                    putString(SCHOOL_CLASS, child.schoolClass).apply()
                    putString(SCHOOL, child.school).apply()
                    putFloat(ACCOUNT, child.account).apply()
                }
            }
        }

    override var userPinCode: String?
        get() = preferencesAuthorization.getString(USER_PIN_CODE, "")
        set(value) = preferencesAuthorization.edit().putString(USER_PIN_CODE, value).apply()

    override var isUserLogged: Boolean
        get() = preferencesAuthorization.getBoolean(IS_USER_LOGGED, false)
        set(value) = preferencesAuthorization.edit().putBoolean(IS_USER_LOGGED, value).apply()

    private fun getChild(): Child? {
        if (isChildWasAdded) {
            with(preferencesChild) {
                return Child(
                    getString(NAME_CHILD, "") ?: "",
                    getString(SCHOOL_CLASS, "") ?: "",
                    getString(SCHOOL, "") ?: "",
                    getFloat(ACCOUNT, 0F),
                )
            }
        }
        return null
    }



    companion object {
        const val IS_USER_LOGGED = "IS_USER_LOGGED"
        const val SHARED_PREFERENCES_AUTHORIZATION = "SHARED_PREFERENCES_AUTHORIZATION"
        const val SHARED_PREFERENCES_CHILD = "SHARED_PREFERENCES_CHILD"
        const val SHARED_PREFERENCES_TIME_PICKED = "SHARED_PREFERENCES_TIME_PICKED"
        const val USER_PIN_CODE = "USER_PIN_CODE"
        const val NAME_CHILD = "NAME_CHILD"
        const val SCHOOL_CLASS = "SCHOOL_CLASS"
        const val SCHOOL = "SCHOOL"
        const val ACCOUNT = "ACCOUNT"
        const val MONDAY = "MONDAY"
        const val TUESDAY = "TUESDAY"
        const val WEDNESDAY = "WEDNESDAY"
        const val THURSDAY = "THURSDAY"
        const val FRIDAY = "FRIDAY"
        const val SATURDAY = "SATURDAY"
        const val SUNDAY = "SUNDAY"
        const val TIME = "TIME"
    }
}