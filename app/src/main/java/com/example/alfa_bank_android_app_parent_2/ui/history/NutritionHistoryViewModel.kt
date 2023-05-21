package com.example.alfa_bank_android_app_parent_2.ui.history

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesChildImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.entiies.HistoryDish
import kotlinx.coroutines.launch

class NutritionHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ParentRepositoryImpl(application.applicationContext)
    private val childPreference =PreferencesChildImpl(application.applicationContext)
    var historyDish:MutableLiveData<List<Any>?> = MutableLiveData<List<Any>?>()

    private val dates = hashSetOf<String>()
    private val newDates = mutableMapOf<String,MutableList<HistoryDish>>()

    fun loadData(){
        viewModelScope.launch {
            var dishes = repository.loadHistoryDish(childPreference.idChild)

            dishes =dishes?.sortedBy {
                it.date
            }

            //Log.d("asdasd",dishes.toString())

            val result = mutableListOf<Any>()
            for (dish in dishes?: listOf()){

                if(!newDates.containsKey(dish.date)){
                    newDates[dish.date] = mutableListOf(dish)
                }else{
                    newDates[dish.date]?.let { l->
                        if(l.contains(dish)){
                            val lastDish = l.find {
                                it.id ==dish.id
                            }

                            l.removeIf { it.id == dish.id }
                            lastDish?.let {
                                l.add(lastDish.copy(
                                    count =((lastDish.count?.toInt() ?: (0 + 1))).toString()
                                ))
                            }

                        }else{
                            l.add(dish)
                        }
                    }
                }

                Log.d("adfadsfasf",newDates.toString())

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