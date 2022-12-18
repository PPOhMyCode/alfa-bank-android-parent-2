package com.example.alfa_bank_android_app_parent_2.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.alfa_bank_android_app_parent_2.domain.preferences.PreferencesChild

class PreferencesChildImpl(context: Context) : PreferencesChild() {
    private val preferencesCopy: SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREFERENCES_CHILD,
            Context.MODE_PRIVATE
        )

    override var idChild: Int
        get() = preferencesCopy.getInt(ID, 0)
        set(value) = preferencesCopy.edit().putInt(ID, value).apply()

    companion object {
        private val SHARED_PREFERENCES_CHILD = "SHARED_PREFERENCES_CHILD"
        private val ID = "ID"
    }
}