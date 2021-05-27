package com.g_one_hospitalapp.models

import com.g_one_hospitalapp.api.responses.HospitalsResponse
import com.google.gson.annotations.SerializedName

data class UserEntity (
    @field:SerializedName("account_type")
    val accountType: String? = "DOCTOR",

    @field:SerializedName("email")
    val email: String? = "",

    @field:SerializedName("telp")
    val noHp: String? ="",

    @field:SerializedName("password")
    val password: String? = "",

    @field:SerializedName("hospital")
    val hospital: HospitalsResponse? = null,

    @field:SerializedName("fcm_token")
    val fcmToken: String? = ""
)