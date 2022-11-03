package com.example.alfa_bank_android_app_parent_2.ui.authentication

import android.app.Application
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationItemsForAdapter
import com.example.alfa_bank_android_app_parent_2.domain.entiies.PinClass
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.data.PreferencesImpl
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode
import com.example.alfa_bank_android_app_parent_2.ui.splashscreen.SplashScreenFragment
import com.example.alfa_bank_android_app_parent_2.ui.splashscreen.SplashScreenFragmentDirections


class AuthenticationViewModel(
    application: Application,
    var fragment: Fragment,
    var pinClass: PinClass,
    var authenticationMode: AuthenticationMode
) : ViewModel() {
    var length = MutableLiveData<Int>()
    val preferences = PreferencesImpl(application.applicationContext)
    var funAfterPinWasEntered: ((s: String?) -> Unit)? = null

    fun loadItemsForAdapter(
        authenticationItemsForAdapter: AuthenticationItemsForAdapter,
        biometricPrompt: BiometricPrompt,
        promptInfo: BiometricPrompt.PromptInfo,
    ) {
        val image = getImageForAdapter(biometricPrompt, promptInfo)

        with(authenticationItemsForAdapter) {
            for (number in 1..9)
                addNumber(AuthenticationItemsForAdapter.ItemNumber(number) {
                    pinClass.addNumber(number)

                    length.value = pinClass.getPin().length
                    if (pinClass.getPin().length == 4) {
                        funAfterPinWasEntered?.let { it(pinClass.getPin()) }
                    }

                })
            addString(AuthenticationItemsForAdapter.ItemString("выход") {
                preferences.isUserLogged = false
                findNavController(fragment as AuthenticationFragment).navigate(
                    AuthenticationFragmentDirections.actionAuthenticationToAuthorization()
                )
            })
            addNumber(AuthenticationItemsForAdapter.ItemNumber(0) {
                pinClass.addNumber(0)
                length.value = pinClass.getPin().length
                if (pinClass.getPin().length == 4) {
                    funAfterPinWasEntered?.let { it(pinClass.getPin()) }
                }
            })
            addImage(image)
        }

    }

    private fun getImageForAdapter(
        biometricPrompt: BiometricPrompt,
        promptInfo: BiometricPrompt.PromptInfo
    ): AuthenticationItemsForAdapter.ItemImage {
        if (pinClass.getPin().isNotEmpty()
            || authenticationMode == AuthenticationMode.INPUT_FIRST_TIME_PASSWORD_MODE
            || authenticationMode == AuthenticationMode.INPUT_SECOND_TIME_PASSWORD_MODE
        ) return AuthenticationItemsForAdapter.ItemImage(R.drawable.ic_baseline_backspace_24) {
            pinClass.removeNumber()
            length.value = pinClass.getPin().length
        }
        return AuthenticationItemsForAdapter.ItemImage(R.drawable.ic_baseline_fingerprint_24) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

}