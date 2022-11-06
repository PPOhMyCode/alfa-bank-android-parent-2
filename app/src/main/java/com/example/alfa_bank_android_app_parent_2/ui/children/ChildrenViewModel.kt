package com.example.alfa_bank_android_app_parent_2.ui.children

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.alfa_bank_android_app_parent_2.data.PreferencesImpl
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child

class ChildrenViewModel(application: Application) : AndroidViewModel(application) {
    val preferences = PreferencesImpl(application.applicationContext)

    fun loadChildren(): List<Child> {
        return listOf(
            Child(
                "Мария Синицина ",
                "6 А класс",
                "МБОУ СОШ №57",
                2300.52F,
            ),
            Child(
                "Михаил Синицина ",
                "10 Б класс",
                "МБОУ СОШ №57",
                0F,
            )
        )
    }
}