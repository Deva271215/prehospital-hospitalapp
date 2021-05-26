package com.g_one_hospitalapp.models

import com.g_one_hospitalapp.api.responses.HospitalsResponse

data class UserEntity (
    val accountType: String? = "NURSE",
    val email: String? = "",
    val noHp: String? ="",
    val password: String? = "",
    val hospital: HospitalsResponse? = null,
)