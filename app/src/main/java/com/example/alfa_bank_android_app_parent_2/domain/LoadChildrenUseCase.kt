package com.example.alfa_bank_android_app_parent_2.domain

class LoadChildrenUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke() = repository.loadChildren()
}