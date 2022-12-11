package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.SerializedName



class AuthorizeBodyDto (
    @SerializedName("Login")
    val login: String,

    @SerializedName("Password")
    val password:String
)
