package com.example.alfa_bank_android_app_parent_2.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesCopyImpl
import com.example.alfa_bank_android_app_parent_2.domain.PreferencesCopy

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    var preferencesCopy: PreferencesCopyImpl = PreferencesCopyImpl(application)
}