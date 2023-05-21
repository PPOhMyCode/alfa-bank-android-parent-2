package com.example.alfa_bank_android_app_parent_2.domain

class LoadShedulerUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(childClass:String ) = repository.loadSheduler(childClass)
}