package com.example.alfa_bank_android_app_parent_2.ui.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesUserImpl

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = PreferencesUserImpl(application.applicationContext)
}