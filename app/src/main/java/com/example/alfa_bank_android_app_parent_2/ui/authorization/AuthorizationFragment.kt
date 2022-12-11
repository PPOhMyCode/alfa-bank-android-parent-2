package com.example.alfa_bank_android_app_parent_2.ui.authorization

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentAuthorizationBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null

    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[AuthorizationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeButtonClick()
        initializeObserve()
    }

    private fun initializeObserve() {
        viewModel.parent.observe(requireActivity()) {
            it?.let {
                findNavController().navigate(
                    AuthorizationFragmentDirections.actionAuthorizationToAuthentication(
                        AuthenticationMode.INPUT_FIRST_TIME_PASSWORD_MODE,
                        null
                    )
                )
            }
        }
    }


    private fun initializeButtonClick() {
        binding.buttonAuthorization.setOnClickListener {
            val login = binding.loginTextInputEditText.text.toString()
            val password = binding.passwordTextInputEditText.text.toString()
            if (login == "")
                Toast.makeText(requireActivity(), "Введите логин", Toast.LENGTH_SHORT).show()
            else if (password == "")
                Toast.makeText(requireActivity(), "Введите пароль", Toast.LENGTH_SHORT).show()
            else if (viewModel.isUserStartLog.value == false) {
                binding.loginTextInputEditText.text?.clear()
                binding.passwordTextInputEditText.text?.clear()
                viewModel.authorization(login, password)
            }
        }
    }

}