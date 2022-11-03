package com.example.alfa_bank_android_app_parent_2.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesImpl

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = PreferencesImpl(application.applicationContext)
}