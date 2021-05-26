package com.g_one_hospitalapp.api.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("error")
    val error: Boolean?,

    @field:SerializedName("statusCode")
    val statusCode: Int?,

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("data")
    val data: SignUpData?
)

data class LoginData (
    @field:SerializedName("user")
    val user: UserResponse?,

    @field:SerializedName("access_token")
    val accessToken: String,
)