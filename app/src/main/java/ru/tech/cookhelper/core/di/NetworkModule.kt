package ru.tech.cookhelper.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.data.remote.api.chat.ChatApi
import ru.tech.cookhelper.data.remote.api.ingredients.FridgeApi
import ru.tech.cookhelper.data.remote.api.user.UserApi
import ru.tech.cookhelper.data.remote.web_socket.feed.FeedService
import ru.tech.cookhelper.data.remote.web_socket.feed.FeedServiceImpl
import ru.tech.cookhelper.data.remote.web_socket.message.MessageService
import ru.tech.cookhelper.data.remote.web_socket.message.MessageServiceImpl
import ru.tech.cookhelper.data.remote.web_socket.user.UserService
import ru.tech.cookhelper.data.remote.web_socket.user.UserServiceImpl
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideChatApi(
        retrofit: Retrofit
    ): ChatApi = retrofit.create(ChatApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideFridgeApi(
        retrofit: Retrofit
    ): FridgeApi = retrofit.create(FridgeApi::class.java)

    @Provides
    @Singleton
    fun provideMessageService(
        jsonParser: JsonParser
    ): MessageService = MessageServiceImpl(jsonParser)

    @Provides
    @Singleton
    fun provideFeedService(
        jsonParser: JsonParser
    ): FeedService = FeedServiceImpl(jsonParser)

    @Provides
    @Singleton
    fun provideUserService(
        jsonParser: JsonParser
    ): UserService = UserServiceImpl(jsonParser)

}