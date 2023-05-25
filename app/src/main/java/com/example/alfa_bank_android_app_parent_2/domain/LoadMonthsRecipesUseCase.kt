package com.example.alfa_bank_android_app_parent_2.domain

class LoadMonthsRecipesUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(childId:Int, year:String) = repository.loadMonthsRecipes(childId,year)
}