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

    //private var isChildWasAdded: Boolean = false

    override var userChild: Child?
        get() = getChild()
        set(value) {
            preferencesChild.edit().putBoolean("IS_CHILD_WAS_ADDED",false).apply()
            value?.let { child ->
                preferencesChild.edit().putBoolean("IS_CHILD_WAS_ADDED",true).apply()
                with(preferencesChild.edit()) {
                    putString(FIRST_CHILD, child.firstName).apply()
                    putString(LAST_NAME, child.lastName).apply()
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
        if (preferencesChild.getBoolean(IS_CHILD_WAS_ADDED,false)) {
            with(preferencesChild) {
                return Child(
                    getString(FIRST_CHILD, "") ?: "",
                    getString(LAST_NAME, "") ?: "",
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
        const val IS_CHILD_WAS_ADDED = "IS_CHILD_WAS_ADDED"
        const val USER_PIN_CODE = "USER_PIN_CODE"
        const val FIRST_CHILD = "FIRST_CHILD"
        const val LAST_NAME = "LAST_NAME"
        const val SCHOOL_CLASS = "SCHOOL_CLASS"
        const val SCHOOL = "SCHOOL"
        const val ACCOUNT = "ACCOUNT"
    }
}