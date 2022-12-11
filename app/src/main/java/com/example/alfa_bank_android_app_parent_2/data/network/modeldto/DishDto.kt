package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DishDto(
    @SerializedName("Date")
    @Expose
    val Date: String,

    @SerializedName("DishId")
    @Expose
    val DishId: Int,

    @SerializedName("MenuId")
    @Expose
    val MenuId: Int,

    @SerializedName("TypeMealId")
    @Expose
    val TypeMealId: Int
)