package com.example.alfa_bank_android_app_parent_2.ui.authentication

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationItemsForAdapter
import com.example.alfa_bank_android_app_parent_2.domain.entiies.PinClass
import com.example.alfa_bank_android_app_parent_2.ui.adapters.AuthenticationCardAdapter
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentAuthenticationBinding
import com.example.alfa_bank_android_app_parent_2.domain.entiies.AuthenticationMode
import com.example.alfa_bank_android_app_parent_2.ui.AuthorizationActivity
import com.example.alfa_bank_android_app_parent_2.ui.MainActivity
import java.util.concurrent.Executor


class AuthenticationFragment : Fragment() {

    private lateinit var pinClass: PinClass

    private val args by navArgs<AuthenticationFragmentArgs>()

    private val viewModelFactory by lazy {
        AuthenticationViewModelFactory(
            requireActivity().application,
            this,
            pinClass,
            args.authenticationMode
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]
    }

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding: FragmentAuthenticationBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthenticationBinding == null")

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authenticationCardAdapter: AuthenticationCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pinClass = PinClass(
            listOf(
                binding.buttonUncheked1,
                binding.buttonUncheked2,
                binding.buttonUncheked3,
                binding.buttonUncheked4
            )
        )
        initializeMode()

        initializeBiometricAuthentication()
        initializeRecyclerView()
        viewModel.pinClass.adapter = {
            changeAuthenticationDataAdapter()
        }

        //if (args.authenticationMode == A) {
        //    biometricPrompt.authenticate(promptInfo)
        //}

    }

    private fun initializeMode() {

        when (args.authenticationMode) {
            AuthenticationMode.INPUT_FIRST_TIME_PASSWORD_MODE -> {
                startFirstTimeMode()
            }
            AuthenticationMode.INPUT_SECOND_TIME_PASSWORD_MODE -> {
                startSecondTimeMode()
            }
            AuthenticationMode.AUTHENTICATION_MODE  -> {
                startAuthenticationMode()
            }

        }
    }

    private fun startFirstTimeMode() {
        binding.text.text = getString(R.string.input_pin)
        viewModel.funAfterPinWasEntered = {

            findNavController().navigate(
                AuthenticationFragmentDirections.actionAuthenticationSelf(
                    AuthenticationMode.INPUT_SECOND_TIME_PASSWORD_MODE,
                    it
                )

            )
            //val intent = it?.let { it1 -> newIntentInputSecondTimePinCode(this, it1) }
            //this.startActivity(intent)
            //finish()
        }
    }

    private fun startSecondTimeMode() {
        binding.text.text = getString(R.string.input_second_time_pin)
        viewModel.funAfterPinWasEntered = {
            if (args.pin == it) {
                it?.let { pinCode ->
                    viewModel.preferences.userPinCode = pinCode
                }
                viewModel.preferences.isUserLogged = true
                viewModel.pinClass.removePin()
                val activity = requireActivity()
                val intent = Intent(activity,MainActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            } else {
                for (circle in viewModel.pinClass.circles) {
                    animateIncorrectPasswordView(circle, 6, 140f, 20f)
                }
            }
        }
    }

    private fun animateIncorrectPasswordView(
        view: View,
        countAction: Int,
        value: Float,
        stepValue: Float,
        nowDeep: Int = 0
    ) {
        if (nowDeep == countAction) {
            ViewCompat.animate(view)
                .translationX(0f)
                .setDuration(100L)
                .withEndAction {
                    viewModel.pinClass.removePin()
                }
                .start()
            return
        }
        ViewCompat.animate(view)
            .translationX(value)
            .setDuration(100L)
            .withEndAction {
                animateIncorrectPasswordView(
                    view,
                    countAction,
                    (value - stepValue) * -1,
                    stepValue * -1,
                    nowDeep + 1
                )
            }
            .start()
    }

    private fun animateCorrectPasswordView(
        views: List<ImageView>,
        scaleX: Float,
        scaleY: Float,
        alpha: Float,
        duration: Long,
        deep: Int,
        count: Int
    ) {
        if (views.size < 4)
            return
        if (deep - 1 == count) {
            val activity = requireActivity()
            val intent = Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
        ViewCompat.animate(views[0])
            .scaleY(scaleY)
            .scaleX(scaleX)
            .alpha(alpha)
            .setDuration(duration)
            .withEndAction {
                ViewCompat.animate(views[1])
                    .scaleY(scaleY)
                    .scaleX(scaleX)
                    .alpha(alpha)
                    .setDuration(duration)
                    .withEndAction {
                        ViewCompat.animate(views[2])
                            .scaleY(scaleY)
                            .scaleX(scaleX)
                            .alpha(alpha)
                            .setDuration(duration)
                            .withEndAction {
                                ViewCompat.animate(views[3])
                                    .scaleY(scaleY)
                                    .scaleX(scaleX)
                                    .alpha(alpha)
                                    .setDuration(duration)
                                    .withEndAction {
                                        if (count % 2 == 0) {
                                            animateCorrectPasswordView(
                                                viewModel.pinClass.circles,
                                                1F, 1F, 1F, duration, deep, count + 1
                                            )
                                        } else {
                                            animateCorrectPasswordView(
                                                viewModel.pinClass.circles,
                                                0.7F, 0.7F, 0.4F, duration, deep, count + 1
                                            )
                                        }
                                    }
                            }
                    }
            }
    }

    private fun startAuthenticationMode() {
        binding.text.visibility = View.INVISIBLE
        viewModel.funAfterPinWasEntered = {
            if (viewModel.preferences.userPinCode.toString() == it) {
                animateCorrectPasswordView(
                    viewModel.pinClass.circles,
                    0.7F, 0.7F, 0.4F, 70L, 4, 0
                )
            } else {
                for (circle in viewModel.pinClass.circles) {
                    animateIncorrectPasswordView(circle, 6, 140f, 20f)
                }
            }
        }
    }

    private fun initializeBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(requireActivity())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.pinClass.addAllPinCode()
                    animateCorrectPasswordView(
                        viewModel.pinClass.circles,
                        0.7F, 0.7F, 0.4F, 70L, 4, 0
                    )
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setTitle("Вход с отпечатком")
            .setSubtitle("Прикоснитесь к сенсору для входа по отпечатку")
            .setNegativeButtonText("Отмена")
            .build()
    }

    private fun initializeRecyclerViewAdapter() {
        val authenticationItemsForAdapter = AuthenticationItemsForAdapter()
        viewModel.loadItemsForAdapter(
            authenticationItemsForAdapter,
            biometricPrompt,
            promptInfo,
        )
        authenticationCardAdapter =
            AuthenticationCardAdapter(authenticationItemsForAdapter, args.authenticationMode)

    }


    private fun initializeRecyclerView() {
        initializeRecyclerViewAdapter()
        initializeButtonsRecyclerView()
    }

    fun changeAuthenticationDataAdapter() {
        val authenticationItemsForAdapter = AuthenticationItemsForAdapter()
        viewModel.loadItemsForAdapter(
            authenticationItemsForAdapter,
            biometricPrompt,
            promptInfo,

            )
        authenticationCardAdapter.authenticationItemsForAdapter = authenticationItemsForAdapter
        authenticationCardAdapter.notifyItemChanged(11)
    }

    private fun initializeButtonsRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 3)
        with(binding.buttonsRecyclerView) {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = authenticationCardAdapter
        }
    }

}