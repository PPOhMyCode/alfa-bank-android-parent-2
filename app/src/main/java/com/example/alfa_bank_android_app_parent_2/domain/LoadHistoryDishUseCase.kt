package com.example.alfa_bank_android_app_parent_2.domain

class LoadHistoryDishUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(idChild:Int) = repository.loadHistoryDish(idChild)
}