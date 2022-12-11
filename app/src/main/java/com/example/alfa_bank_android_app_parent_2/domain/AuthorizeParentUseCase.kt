package com.example.alfa_bank_android_app_parent_2.domain

import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent

class AuthorizeParentUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(login: String, password: String): Parent? =
        repository.authorizeParent(login, password)
}