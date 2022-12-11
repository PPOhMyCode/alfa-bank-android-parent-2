package com.example.alfa_bank_android_app_parent_2.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.alfa_bank_android_app_parent_2.domain.preferences.PreferencesNotification
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Notification
import java.sql.Time
import java.time.DayOfWeek

class PreferencesNotificationImpl(context: Context) : PreferencesNotification() {

    private val preferencesNotification: SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREFERENCES_NOTIFICATION,
            Context.MODE_PRIVATE
        )


    override fun getNotifications(): List<Notification> {
        val lastId = lastIdPreferences
        val result = mutableListOf<Notification>()
        for (id in 1..lastId) {
            if (!preferencesNotification.getBoolean(IS_DELETED + id, false))
                result.add(getNotification(id))
        }
        return result
    }

    override var lastRequestCode: Int
        get() = preferencesNotification.getInt(LAST_REQUEST_CODE, -1)
        set(value) = preferencesNotification.edit().putInt(LAST_REQUEST_CODE, value).apply()


    override fun addNotification(notification: Notification) {
        with(notification) {
            lastRequestCode = requestCode
            val idNotification = lastIdPreferences + 1
            lastIdPreferences = idNotification
            addDaysOfWeek(idNotification, daysOfWeek)
            addTime(idNotification, time)
            addRequestCode(idNotification, requestCode)
            addOnPause(idNotification, isNotOnPause)
        }
    }

    override fun changeStateOnPause(id: Int, isNotOnPause: Boolean) {
        preferencesNotification.edit().putBoolean(ON_PAUSE + id, isNotOnPause).apply()
    }

    override fun deleteNotification(id: Int) =
        preferencesNotification.edit().putBoolean(IS_DELETED + id, true).apply()

    private fun addOnPause(id: Int, isOnPause: Boolean) {
        preferencesNotification.edit().putBoolean(ON_PAUSE + id, isOnPause).apply()
    }

    private fun addRequestCode(id: Int, requestCode: Int) {
        preferencesNotification.edit().putInt(REQUEST_CODE + id, requestCode).apply()
    }

    private fun addDaysOfWeek(id: Int, daysOfWeek: List<DayOfWeek>) {
        for (day in daysOfWeek)
            preferencesNotification.edit().putBoolean(DAY_OF_WEEK + day + id, true).apply()

    }

    private fun addTime(id: Int, time: Time) {
        with(preferencesNotification.edit()) {
            putInt(TIME_NOTIFICATION + "HOUR" + id.toString(), time.hours).apply()
            putInt(TIME_NOTIFICATION + "MINUTE" + id.toString(), time.minutes).apply()
        }
    }

    private fun getNotification(id: Int): Notification {
        return Notification(
            id,
            getDaysOfWeek(id),
            getTimeNotification(id),
            getRequestCode(id),
            getIsOnPause(id)
        )
    }

    private fun getIsOnPause(id: Int) =
        preferencesNotification.getBoolean(ON_PAUSE + id, false)


    private fun getDaysOfWeek(id: Int): List<DayOfWeek> {
        val result = mutableListOf<DayOfWeek>()
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.MONDAY + id, false))
            result.add(DayOfWeek.MONDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.TUESDAY + id, false))
            result.add(DayOfWeek.TUESDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.WEDNESDAY + id, false))
            result.add(DayOfWeek.WEDNESDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.THURSDAY + id, false))
            result.add(DayOfWeek.THURSDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.FRIDAY + id, false))
            result.add(DayOfWeek.FRIDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.SATURDAY + id, false))
            result.add(DayOfWeek.SATURDAY)
        if (preferencesNotification.getBoolean(DAY_OF_WEEK + DayOfWeek.SUNDAY + id, false))
            result.add(DayOfWeek.SUNDAY)
        return result
    }

    private fun getTimeNotification(id: Int): Time {
        val hour = preferencesNotification.getInt(TIME_NOTIFICATION + "HOUR" + id, -1)
        val minute =
            preferencesNotification.getInt(TIME_NOTIFICATION + "MINUTE" + id, -1)
        if (hour == -1 || minute == -1)
            throw Exception("Hour or minute is -1")
        return Time(hour, minute, 0)
    }

    private fun getRequestCode(id: Int): Int {
        val result = preferencesNotification.getInt(REQUEST_CODE + id, -1)
        if (result == -1)
            throw Exception("RequestCode is -1")
        return result
    }

    private var lastIdPreferences: Int
        get() = preferencesNotification.getInt(LAST_NOTIFICATION_ID, 0)
        set(value) = preferencesNotification.edit().putInt(LAST_NOTIFICATION_ID, value).apply()


    companion object {
        const val LAST_REQUEST_CODE = "LAST_REQUEST_CODE"
        const val SHARED_PREFERENCES_NOTIFICATION = "SHARED_PREFERENCES_NOTIFICATION"
        const val LAST_NOTIFICATION_ID = "LAST_NOTIFICATION_ID"
        const val IS_DELETED = "IS_DELETED"
        const val TIME_NOTIFICATION = "TIME_NOTIFICATION"
        const val REQUEST_CODE = "REQUEST_CODE"
        const val DAY_OF_WEEK = "DAY_OF_WEEK"
        const val ON_PAUSE = "ON_PAUSE"
        // const val MONDAY = "MONDAY"
        //const val TUESDAY = "TUESDAY"
        //const val WEDNESDAY = "WEDNESDAY"
        //const val THURSDAY = "THURSDAY"
        //const val FRIDAY = "FRIDAY"
        //const val SATURDAY = "SATURDAY"
        // const val SUNDAY = "SUNDAY"
        // const val TIME = "TIME"
    }
}