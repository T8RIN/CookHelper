package ru.tech.cookhelper.data.remote.web_socket.protocol

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

interface FeedService : Service {
    operator fun invoke(token: String): Flow<WebSocketState<List<RecipePostDto>>>
}