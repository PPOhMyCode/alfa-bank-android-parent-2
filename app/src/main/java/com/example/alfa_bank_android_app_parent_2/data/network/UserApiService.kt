package com.example.alfa_bank_android_app_parent_2.data.network

import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.ChildContainerDto
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.DescriptionDish
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.DishDtoContainer
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("/users/{userId}/childrens")
    suspend fun loadChildren(@Path("userId") userId: String): ChildContainerDto

    @GET("/menus/date/{date}/type/{type}")
    suspend fun loadDishes(
        @Path("date") date: String,
        @Path("type") type: String
    ): DishDtoContainer

    @GET("/dishes/{id}")
    suspend fun loadDish(@Path("id") id: String):DescriptionDish
}