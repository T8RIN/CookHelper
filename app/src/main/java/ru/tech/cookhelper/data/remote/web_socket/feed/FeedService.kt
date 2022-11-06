package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.data.remote.dto.RecipeDto
import ru.tech.cookhelper.data.remote.web_socket.Service
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState

interface FeedService : Service {
    operator fun invoke(token: String): Flow<WebSocketState<List<RecipeDto>>>
}