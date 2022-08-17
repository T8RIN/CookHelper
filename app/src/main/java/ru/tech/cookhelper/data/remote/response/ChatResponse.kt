package ru.tech.cookhelper.data.remote.response

import ru.tech.cookhelper.data.remote.dto.MessageDto
import ru.tech.cookhelper.data.remote.utils.Response

data class ChatResponse(
    val chat: List<MessageDto>,
    override val message: String,
    override val status: Int
) : Response