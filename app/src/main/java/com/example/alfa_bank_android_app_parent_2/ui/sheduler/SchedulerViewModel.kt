package com.example.alfa_bank_android_app_parent_2.ui.sheduler

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.LoadShedulerUseCase
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ScheduleItem
import kotlinx.coroutines.launch

class SchedulerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ParentRepositoryImpl(application.applicationContext)
    private val loadShedulerUseCase = LoadShedulerUseCase(repository)
    private var childClass = PreferencesChildImpl(application.applicationContext).schoolClass

    var shedulers: MutableLiveData<List<ScheduleItem>> = MutableLiveData<List<ScheduleItem>>()

    fun loadSheduler(){
        viewModelScope.launch {
            shedulers.value = loadShedulerUseCase.invoke(childClass)
        }
    }

}