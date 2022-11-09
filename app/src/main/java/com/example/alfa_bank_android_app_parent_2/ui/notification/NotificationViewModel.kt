package com.example.alfa_bank_android_app_parent_2.ui.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesImpl

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = PreferencesImpl(application.applicationContext)
}