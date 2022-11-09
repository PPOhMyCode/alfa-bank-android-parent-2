package com.example.alfa_bank_android_app_parent_2.data

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.example.alfa_bank_android_app_parent_2.domain.Preferences
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.TimePicked
import java.sql.Time


class PreferencesImpl(context: Context) : Preferences() {
    private val preferencesAuthorization: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_AUTHORIZATION, Context.MODE_PRIVATE)

    private val preferencesChild: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_CHILD, Context.MODE_PRIVATE)

    private var preferencesTimePicked: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_TIME_PICKED, Context.MODE_PRIVATE)

    private var isChildWasAdded: Boolean = false

    override var timePicked: TimePicked
        get() = getTimePicked()
        set(value) {
            with(preferencesTimePicked.edit()) {
                putBoolean(MONDAY, value.monday).apply()
                putBoolean(TUESDAY, value.tuesday).apply()
                putBoolean(WEDNESDAY, value.wednesday).apply()
                putBoolean(THURSDAY, value.thursday).apply()
                putBoolean(FRIDAY, value.friday).apply()
                putBoolean(SATURDAY, value.saturday).apply()
                putBoolean(SUNDAY, value.sunday).apply()
                putString(TIME, value.time.toString()).apply()
            }
        }

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

    @JvmName("getTimePicked1")
    private fun getTimePicked(): TimePicked {
        with(preferencesTimePicked) {
            var time= getString(TIME,"13:10:00")
            return TimePicked(
                getBoolean(MONDAY,false),
                getBoolean(TUESDAY,false),
                getBoolean(WEDNESDAY,false),
                getBoolean(THURSDAY,false),
                getBoolean(FRIDAY,false),
                getBoolean(SATURDAY,false),
                getBoolean(SUNDAY,false),
                Time.valueOf(getString(TIME,"12:00:00"))
            )
        }
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