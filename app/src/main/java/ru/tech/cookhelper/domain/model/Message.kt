package ru.tech.cookhelper.domain.model

import com.squareup.moshi.Json

data class Message(
    val id: Long,
    @field:Json(name = "time_stamp")
    val timestamp: Long,
    val text: String,
    @field:Json(name = "attachment_id")
    val attachmentId: String?,
    @field:Json(name = "user_id")
    val userId: Long
)
