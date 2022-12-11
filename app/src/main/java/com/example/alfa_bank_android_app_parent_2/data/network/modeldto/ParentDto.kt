package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ParentDto(
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("user")
    @Expose
    val user: String
)