package ru.tech.cookhelper.data.remote.api.chat

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tech.cookhelper.data.remote.dto.ChatDto
import ru.tech.cookhelper.data.remote.utils.Response

interface ChatApi {

    @GET("api/chat/get/by-id/")
    suspend fun getChat(
        @Query("id") chatId: Long,
        @Query("token") token: String
    ): Response<ChatDto>

    @GET("api/chat/get")
    suspend fun getChatList(
        @Query("token") token: String
    ): String

}