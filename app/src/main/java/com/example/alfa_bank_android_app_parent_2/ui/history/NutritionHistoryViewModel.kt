package com.example.alfa_bank_android_app_parent_2.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import kotlinx.coroutines.launch

class NutritionHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ParentRepositoryImpl(application.applicationContext)
    private val childPreference =PreferencesChildImpl(application.applicationContext)
    var historyDish:MutableLiveData<List<Any>?> = MutableLiveData<List<Any>?>()

    private val dates = hashSetOf<String>()

    fun loadData(){
        viewModelScope.launch {
            var dishes = repository.loadHistoryDish(childPreference.idChild)

            dishes =dishes?.sortedBy {
                it.date
            }

            val result = mutableListOf<Any>()
            for (dish in dishes?: listOf()){
                if(dish.date !in dates){
                    dates.add(dish.date)
                    result.add(dish.date.split("Т")[0])
                }
                result.add(dish)
            }

            historyDish.value = result

        }
    }
}