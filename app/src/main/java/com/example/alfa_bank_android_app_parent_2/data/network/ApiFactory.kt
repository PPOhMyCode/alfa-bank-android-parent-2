package com.example.alfa_bank_android_app_parent_2.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiFactory {
    companion object {
        const val BASE_BD_URL = "https://d5dr8ccmms8urkspiqc2.apigw.yandexcloud.net"
        const val BASE_USER_URL = "https://d5ddrkh28qsp3ulski8l.apigw.yandexcloud.net"

        fun getBdApiService(): BdApiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_BD_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply
                {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                ).build()
            )
            .build()
            .create(BdApiService::class.java)

        fun getUserApiService(url: String =BASE_USER_URL):UserApiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply
                {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                ).build()
            )
            .build()
            .create(UserApiService::class.java)
    }

}