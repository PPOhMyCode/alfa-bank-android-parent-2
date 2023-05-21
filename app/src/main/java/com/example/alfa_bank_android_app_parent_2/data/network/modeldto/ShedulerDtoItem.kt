package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShedulerDtoItem(

    @SerializedName("Time")
    @Expose
    val Time: String,

    @SerializedName("TypeMeal")
    @Expose
    val TypeMeal: String
)