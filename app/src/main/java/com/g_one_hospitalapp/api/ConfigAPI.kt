package com.g_one_hospitalapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigAPI {
    private val URL = "http://192.168.18.10:8080/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder().method(original.method, original.body)
            val request = requestBuilder.build()

            it.proceed(request)
        }.build()

    val instance: ServiceAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ServiceAPI::class.java)
    }
}