package com.g_one_hospitalapp.models

import com.google.gson.annotations.SerializedName
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MessageEntity(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("response")
    val response: String? = null,

    @field:SerializedName("condition")
    val condition: String? = null,

    @field:SerializedName("action")
    val action: String? = null,

    @field:SerializedName("attachments")
    val attachments: String,

    @field:SerializedName("creation_time")
    val creationTime: String? = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
)