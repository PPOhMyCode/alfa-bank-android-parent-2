package com.example.alfa_bank_android_app_parent_2.domain

import androidx.lifecycle.LiveData
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Parent
import retrofit2.http.Path

interface ParentRepository {

    suspend fun authorizeParent(login: String, password: String): Parent?

    suspend fun loadChildren(): List<Child>?

    suspend fun loadDishes(date: String, type: String): List<Dish>?
}