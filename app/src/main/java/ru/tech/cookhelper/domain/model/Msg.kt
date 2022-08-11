package ru.tech.cookhelper.domain.model

data class Msg(
    val attachment_id: String,
    val id: Int,
    val text: String,
    val time_stamp: String,
    val user_id: Int
)