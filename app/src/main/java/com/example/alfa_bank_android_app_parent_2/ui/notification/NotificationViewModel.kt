package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesNotificationImpl
import com.example.alfa_bank_android_app_parent_2.data.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Notification

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesNotification =
        PreferencesNotificationImpl(application.applicationContext)

    fun loadNotifications(): List<Notification> {
        return preferencesNotification.getNotifications()
    }

    fun addNotification(notification: Notification) {
        preferencesNotification.addNotification(notification)
    }

    fun getLastRequestCode():Int{
        return preferencesNotification.lastRequestCode
    }
}