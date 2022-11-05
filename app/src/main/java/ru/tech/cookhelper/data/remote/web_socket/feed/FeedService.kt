package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.remote.web_socket.Service

interface FeedService : Service {
    operator fun invoke(token: String): Flow<WebSocketState<List<RecipePostDto>>>
}