package ru.tech.cookhelper.domain.use_case.close_connection

import ru.tech.cookhelper.data.remote.web_socket.protocol.FeedService
import ru.tech.cookhelper.data.remote.web_socket.protocol.MessageService
import ru.tech.cookhelper.data.remote.web_socket.protocol.UserService
import javax.inject.Inject

class CloseConnectionsUseCase @Inject constructor(
    private val messageService: MessageService,
    private val userService: UserService,
    private val feedService: FeedService
) {
    operator fun invoke() {
        listOf(
            messageService, userService, feedService
        ).forEach { it.closeService() }
    }
}