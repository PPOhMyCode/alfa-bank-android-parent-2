package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesNotificationImpl
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

    fun deleteNotification(notification: Notification){
        notification.idNotification?.let {
            preferencesNotification.deleteNotification(it)
        }
    }

    fun changeStateOnPause(id:Int,isOnPause:Boolean){
        preferencesNotification.changeStateOnPause(id,isOnPause)
    }


}