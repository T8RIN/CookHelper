package ru.tech.cookhelper.data.remote.web_socket.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.web_socket.WebSocketClient
import ru.tech.cookhelper.data.remote.web_socket.WebSocketState
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Inject

class FeedServiceImpl @Inject constructor(
    jsonParser: JsonParser
) : WebSocketClient<List<RecipePostDto>>(jsonParser = jsonParser), FeedService {

    override operator fun invoke(
        token: String
    ): Flow<WebSocketState<List<RecipePostDto>>> = flow {
        emit(WebSocketState.Opening())
    }

    override fun sendMessage(data: String) = send(data)

    override fun closeService() = close()

    //updateBaseUrl(
    //            newBaseUrl = "${Constants.WS_BASE_URL}ws/feed/?token=$token"
    //        ).setType(
    //            Types.newParameterizedType(
    //                List::class.java,
    //                RecipePostDto::class.java
    //            )
    //        ).openWebSocket().receiveAsFlow()


}