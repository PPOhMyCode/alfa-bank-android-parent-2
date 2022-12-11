package com.example.alfa_bank_android_app_parent_2.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.alfa_bank_android_app_parent_2.domain.preferences.PreferencesCopy

class PreferencesCopyImpl(context: Context) : PreferencesCopy() {

    private val preferencesCopy: SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREFERENCES_COPY,
            Context.MODE_PRIVATE
        )

    override var isNeedToDisplayName: Boolean
        get() = preferencesCopy.getBoolean(NAME, true)
        set(value) = preferencesCopy.edit().putBoolean(NAME, value).apply()

    override var isNeedToDisplayDay: Boolean
        get() = preferencesCopy.getBoolean(DAY, true)
        set(value) = preferencesCopy.edit().putBoolean(DAY, value).apply()

    override var isNeedToDisplayBill: Boolean
        get() = preferencesCopy.getBoolean(BILL, true)
        set(value) = preferencesCopy.edit().putBoolean(BILL, value).apply()

    override var isNeedToDisplaySquirrels: Boolean
        get() = preferencesCopy.getBoolean(SQUIRRELS, true)
        set(value) = preferencesCopy.edit().putBoolean(SQUIRRELS, value).apply()

    override var isNeedToDisplayFat: Boolean
        get() = preferencesCopy.getBoolean(FAT, true)
        set(value) = preferencesCopy.edit().putBoolean(FAT, value).apply()

    override var isNeedToDisplayCarbohydrates: Boolean
        get() = preferencesCopy.getBoolean(CARBOHYDRATES, true)
        set(value) = preferencesCopy.edit().putBoolean(CARBOHYDRATES, value).apply()

    override var isNeedToDisplayCalories: Boolean
        get() = preferencesCopy.getBoolean(CALORIES, true)
        set(value) = preferencesCopy.edit().putBoolean(CALORIES, value).apply()

    companion object {
        const val SHARED_PREFERENCES_COPY = "SHARED_PREFERENCES_COPY"
        private const val NAME = "NAME"
        private const val DAY = "DAY"
        private const val BILL = "BILL"
        private const val SQUIRRELS = "SQUIRRELS"
        private const val FAT = "FAT"
        private const val CARBOHYDRATES = "CARBOHYDRATES"
        private const val CALORIES = "CALORIES"
    }

}