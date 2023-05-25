package com.example.alfa_bank_android_app_parent_2.domain

import androidx.lifecycle.LiveData
import com.example.alfa_bank_android_app_parent_2.domain.entiies.*
import retrofit2.http.Path

interface ParentRepository {

    suspend fun authorizeParent(login: String, password: String): Parent?

    suspend fun loadChildren(): List<Child>?

    suspend fun loadDishes(date: String, type: String): List<Dish>?

    suspend fun loadDishesThisWeek(date:String, type: String):List<Dish>?

    suspend fun makeOrder(
        data: String,
        childrenId: Int,
        typeMeal: Int,
        dishId: Int
    ): String

    suspend fun loadHistoryDish(idChild:Int):List<HistoryDish>?

    suspend fun loadSheduler(childClass:String ):List<ScheduleItem>?

    suspend fun loadYearsRecipes(idChild: Int):List<String>

    suspend fun loadMonthsRecipes(idChild: Int, year: String): List<String>


}