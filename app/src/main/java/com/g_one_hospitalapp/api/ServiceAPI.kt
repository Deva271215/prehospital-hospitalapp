package com.g_one_hospitalapp.api

import com.g_one_hospitalapp.api.responses.*
import com.g_one_hospitalapp.models.UserEntity
import retrofit2.Call
import retrofit2.http.*

interface ServiceAPI {
    // Authentication
    @POST("users")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun createUser(
        @Body user: UserEntity
    ): Call<SignUpResponse>

    @POST("auth/sign-in")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun signInUser(
        @Body user: UserEntity
    ): Call<LoginResponse>

    // Hospitals
    @GET("hospitals")
    fun getHospitals(): Call<List<HospitalsResponse>>

    // Message
    @GET("messages/chat/{id}")
    fun getMessages(
        @Path("id") id: String,
        @Header("Authorization") header: String
    ): Call<ArrayList<MessageResponse>>

    // Chat
    @GET("chats")
    fun getChats(@Header("Authorization") header: String): Call<ArrayList<ChatResponse>>

    @GET("chats/hospital/{id}")
    fun getChatsByHospital(
            @Path("id") id: String,
            @Header("Authorization") header: String
    ): Call<ArrayList<ChatResponse>>
}