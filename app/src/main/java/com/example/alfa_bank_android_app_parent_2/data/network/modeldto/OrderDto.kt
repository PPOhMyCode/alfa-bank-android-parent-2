package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("ChildrenId")
    val childrenId: Int,
    @SerializedName("Date")
    val date: String,
    @SerializedName("DishId")
    val dishId: Int,
    @SerializedName("TypeMealId")
    val typeMealId: Int
)