package com.example.alfa_bank_android_app_parent_2.ui.authorization

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.alfa_bank_android_app_parent_2.data.preferences.PreferencesUserImpl
import com.example.alfa_bank_android_app_parent_2.data.repository.ParentRepositoryImpl
import com.example.alfa_bank_android_app_parent_2.domain.AuthorizeParentUseCase
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent
import kotlinx.coroutines.launch

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    val preferences = PreferencesUserImpl(application.applicationContext)

    var parent: MutableLiveData<Parent> = MutableLiveData<Parent>()
    var isUserStartLog: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val repositoryImpl = ParentRepositoryImpl(application.applicationContext)
    private val authorizeParentUseCase = AuthorizeParentUseCase(repositoryImpl)


    fun authorization(login: String, password: String) {
        isUserStartLog.value = true
        viewModelScope.launch {
            parent.value = authorizeParentUseCase.invoke(login, password)
            isUserStartLog.value = false
            parent.value?.let { preferences.user = it }
        }
    }
}