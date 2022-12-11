package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DescriptionDish(
    @SerializedName("Calories")
    @Expose
    val Calories: Double,

    @SerializedName("Carbohydrates")
    @Expose
    val Carbohydrates: Double,

    @SerializedName("Cost")
    @Expose
    val Cost: Double,

    @SerializedName("Description")
    @Expose
    val Description: String,

    @SerializedName("Fats")
    @Expose
    val Fats: Double,

    @SerializedName("Id")
    @Expose
    val Id: Int,

    @SerializedName("Name")
    @Expose
    val Name: String,

    @SerializedName("Proteins")
    @Expose
    val Proteins: Double,

    @SerializedName("Weight")
    @Expose
    val Weight: Double
)