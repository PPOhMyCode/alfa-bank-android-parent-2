package com.example.alfa_bank_android_app_parent_2.ui.splashscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[SplashScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash__screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1000)
            when (viewModel.preferences.isUserLogged) {
                true -> launchAuthorizationFragment()
                false -> launchAuthenticationFragment()
            }
        }

    }

    private fun launchAuthenticationFragment() {
        findNavController().navigate(
            SplashScreenFragmentDirections
                .actionSplashScreenToAuthorization()
        )
    }

    private fun launchAuthorizationFragment() {
        findNavController().navigate(
            SplashScreenFragmentDirections
                .actionSplashScreenToAuthentication(
                    AuthenticationMode.AUTHENTICATION_MODE,
                    null
                )
        )
    }
}