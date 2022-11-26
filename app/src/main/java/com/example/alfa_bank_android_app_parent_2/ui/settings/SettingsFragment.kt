package com.example.alfa_bank_android_app_parent_2.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }
    private lateinit var _binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeCheckBoxes()
    }

    private fun initializeCheckBoxes() {
        with(viewModel.preferencesCopy) {
            _binding.nameCheckBox.isChecked = isNeedToDisplayName
            _binding.dayCheckBox.isChecked = isNeedToDisplayDay
            _binding.billCheckBox.isChecked = isNeedToDisplayBill
            _binding.carbohydratesCheckBox.isChecked = isNeedToDisplayCarbohydrates
            _binding.squirrelsCheckBox.isChecked = isNeedToDisplaySquirrels
            _binding.fatCheckBox.isChecked = isNeedToDisplayFat
            _binding.caloriesCheckBox.isChecked = isNeedToDisplayCalories
        }
        initializeCheckedChangeListener()
    }

    private fun initializeCheckedChangeListener(){
        with(viewModel.preferencesCopy) {
            _binding.nameCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayName=isChecked
            }
            _binding.dayCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayDay=isChecked
            }
            _binding.billCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayBill=isChecked
            }
            _binding.carbohydratesCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayCarbohydrates=isChecked
            }
            _binding.squirrelsCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplaySquirrels=isChecked
            }
            _binding.fatCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayFat=isChecked
            }
            _binding.caloriesCheckBox.setOnCheckedChangeListener { _, isChecked ->
                isNeedToDisplayCalories=isChecked
            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

}