package com.example.alfa_bank_android_app_parent_2.ui.receipts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.*
import kotlinx.coroutines.launch

class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ParentRepositoryImpl(application.applicationContext)
    private val loadYearsRecipesUseCase = LoadYearsRecipesUseCase(repository)
    private val loadMonthsRecipesUseCase = LoadMonthsRecipesUseCase(repository)
    private var childId = PreferencesChildImpl(application.applicationContext).idChild

    var month: String? =null
    var years: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    var months: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    fun loadYears(){
        viewModelScope.launch {
            years.value = loadYearsRecipesUseCase.invoke(childId)
        }
    }

    fun loadMonths(){
        month?.let {
            viewModelScope.launch {
                months.value = loadMonthsRecipesUseCase.invoke(childId, it)
            }
        }
    }
}