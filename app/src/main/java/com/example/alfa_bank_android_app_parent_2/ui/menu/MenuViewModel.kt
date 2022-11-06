package com.example.alfa_bank_android_app_parent_2.ui.menu

import androidx.lifecycle.ViewModel
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish

class MenuViewModel : ViewModel() {
    fun getDishes(): List<Dish> {
        return listOf(
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F),
            Dish("Каша овсяная", listOf("Вода", "Соль"), 150F, 85F)
        )
    }
}