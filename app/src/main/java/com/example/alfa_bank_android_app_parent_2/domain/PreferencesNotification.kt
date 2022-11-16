package com.example.alfa_bank_android_app_parent_2.domain

import com.example.alfa_bank_android_app_parent_2.domain.entiies.Notification

abstract class PreferencesNotification() {
    abstract fun getNotifications():List<Notification>
    abstract var lastRequestCode:Int
    abstract fun addNotification(notification: Notification)
    abstract fun deleteNotification(id: Int)
    abstract fun changeStateOnPause(id:Int, isNotOnPause:Boolean)
    //abstract var changeLastRequestCode:(Int)->Unit
}