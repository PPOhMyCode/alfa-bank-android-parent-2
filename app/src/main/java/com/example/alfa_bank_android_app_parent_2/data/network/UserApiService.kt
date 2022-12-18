package com.example.alfa_bank_android_app_parent_2.data.network

import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    suspend fun loadDish(@Path("id") id: String): DescriptionDish

    @GET("/history/children/{id}")
    suspend fun loadHistoryDishes(@Path("id") id: String): HistoryDishDtoContainer

    @GET("/orders/date/{date}/children/{childrenId}/type/{type}")
    suspend fun loadSelectedDishes(
        @Path("date") date: String,
        @Path("childrenId") childrenId: String,
        @Path("type") type:String
    ): ThisWeekDishContainer

    @POST("/orders")
    suspend fun makeOrder(@Body orderBody: OrderDto)

}