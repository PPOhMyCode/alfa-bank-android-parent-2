package com.example.alfa_bank_android_app_parent_2.domain

class LoadYearsRecipesUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(childId:Int) = repository.loadYearsRecipes(childId)
}