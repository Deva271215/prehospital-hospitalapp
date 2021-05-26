package com.g_one_hospitalapp.api.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("group_name")
    val group_name: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("telp")
    val telp: String?,

    @field:SerializedName("is_deleted")
    val is_deleted: Boolean?,

    @field:SerializedName("created_at")
    val created_at: String?,

    @field:SerializedName("updated_at")
    val updated_at: String?,

    @field:SerializedName("deleted_at")
    val deleted_at: String?,
)