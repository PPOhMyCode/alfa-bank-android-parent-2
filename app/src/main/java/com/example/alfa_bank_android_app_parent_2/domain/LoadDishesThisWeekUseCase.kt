package com.example.alfa_bank_android_app_parent_2.domain

class LoadDishesThisWeekUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(date:String, type: String) = repository.loadDishesThisWeek(date,type)
}