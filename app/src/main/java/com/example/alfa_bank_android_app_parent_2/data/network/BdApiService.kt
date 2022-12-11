package com.example.alfa_bank_android_app_parent_2.data.network

import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.AuthorizeBodyDto
import com.example.alfa_bank_android_app_parent_2.data.network.modeldto.ParentDto
import retrofit2.http.*


interface BdApiService {
    @POST("/authorization")
    suspend fun authorizeParent(@Body authorizeBody: AuthorizeBodyDto): ParentDto

}