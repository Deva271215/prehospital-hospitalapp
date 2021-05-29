package com.g_one_hospitalapp.api.responses

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: String?,

    @field:SerializedName("response")
    val response: String?,

    @field:SerializedName("condition")
    val condition: String?,

    @field:SerializedName("action")
    val action: String,

    @field:SerializedName("attachments")
    val attachments: String?,

    @field:SerializedName("is_deleted")
    val isDeleted: Boolean?,

    @field:SerializedName("created_at")
    val createdAt: String?,

    @field:SerializedName("updated_at")
    val updatedAt: String?,

    @field:SerializedName("deleted_at")
    val deletedAt: String?
)