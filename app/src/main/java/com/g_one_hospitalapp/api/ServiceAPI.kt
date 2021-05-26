package com.g_one_hospitalapp.api

import com.g_one_hospitalapp.api.responses.HospitalsResponse
import com.g_one_hospitalapp.api.responses.SignUpResponse
import com.g_one_hospitalapp.models.UserEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceAPI {
    @POST("users")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun createUser(
        @Body user: UserEntity
    ): Call<SignUpResponse>

    // Hospitals
    @GET("hospitals")
    fun getHospitals(): Call<List<HospitalsResponse>>
}