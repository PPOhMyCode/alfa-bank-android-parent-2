package com.example.alfa_bank_android_app_parent_2.ui.authentication

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alfa_bank_android_app_parent_2.domain.entiies.PinClass
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode

class AuthenticationViewModelFactory(
    var application2: Application,
    var fragment: Fragment,
    var pinClass: PinClass,
    var authenticationMode: AuthenticationMode
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(application2,fragment, pinClass, authenticationMode) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}