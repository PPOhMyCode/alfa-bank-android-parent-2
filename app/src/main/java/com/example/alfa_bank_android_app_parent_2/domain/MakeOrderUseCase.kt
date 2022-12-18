package com.example.alfa_bank_android_app_parent_2.domain

class MakeOrderUseCase(private val repository: ParentRepository) {

    suspend operator fun invoke(
        data: String,
        childrenId: Int,
        typeMeal: Int,
        dishId: Int
    ) = repository.makeOrder(data,childrenId,typeMeal,dishId)
}