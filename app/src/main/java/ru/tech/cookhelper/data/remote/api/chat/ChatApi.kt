package ru.tech.cookhelper.data.remote.api.chat

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tech.cookhelper.data.remote.response.ChatResponse

interface ChatApi {

    @GET("api/chat/get/messages")
    suspend fun getAllMessages(
        @Query("id") chatId: String,
        @Query("token") token: String
    ): ChatResponse

    @GET("api/chat/get")
    suspend fun getChatList(
        @Query("token") token: String
    ): String

}