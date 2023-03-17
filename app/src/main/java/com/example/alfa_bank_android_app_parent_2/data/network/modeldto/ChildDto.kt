package com.example.alfa_bank_android_app_parent_2.data.network.modeldto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChildDto(

    @SerializedName("ChildrenId")
    @Expose
    val ChildrenId: Int,

    @SerializedName("FirstName")
    @Expose
    val FirstName: String,

    @SerializedName("Grade")
    @Expose
    val GradeId: String,

    @SerializedName("ParentID")
    @Expose
    val ParentID: Int,

    @SerializedName("Patronymic")
    @Expose
    val Patronymic: String,

    @SerializedName("SecondName")
    @Expose
    val SecondName: String
)