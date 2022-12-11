package com.example.alfa_bank_android_app_parent_2.ui.children

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.LoadChildrenUseCase
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import kotlinx.coroutines.launch

class ChildrenViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = PreferencesUserImpl(application.applicationContext)
    private val repository = ParentRepositoryImpl(application.applicationContext)
    private val loadChildrenUseCase = LoadChildrenUseCase(repository)

    val children = MutableLiveData<List<Child>?>()

    fun loadChildren() {
        viewModelScope.launch {
            val loadChildren =loadChildrenUseCase.invoke()
            children.value = loadChildren as List<Child>
        }
    }
}