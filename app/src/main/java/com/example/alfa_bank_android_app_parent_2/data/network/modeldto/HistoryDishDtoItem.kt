package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.SerializedName

data class HistoryDishDtoItem(

    @SerializedName("Cost")
    val cost: Double,

    @SerializedName("Date")
    val date: String,

    @SerializedName("DishId")
    val dishId: Int,

    @SerializedName("Name")
    val name: String,

    @SerializedName("Weight")
    val weight: Double
)