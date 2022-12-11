package com.example.alfa_bank_android_app_parent_2.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesCopyImpl

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    var preferencesCopy: PreferencesCopyImpl = PreferencesCopyImpl(application)
}