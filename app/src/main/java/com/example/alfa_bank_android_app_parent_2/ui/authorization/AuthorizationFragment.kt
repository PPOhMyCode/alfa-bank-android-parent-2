package com.example.alfa_bank_android_app_parent_2.ui.authorization

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode

class AuthorizationFragment : Fragment() {
    private lateinit var buttonAuthorization: Button
    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonAuthorization).setOnClickListener {
            findNavController().navigate (
                AuthorizationFragmentDirections.actionAuthorizationToAuthentication(
                    AuthenticationMode.INPUT_FIRST_TIME_PASSWORD_MODE,
                    null
                )
            )
        }
    }

}