package ru.tech.cookhelper.core.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tech.cookhelper.data.local.database.CookHelperDatabase
import ru.tech.cookhelper.data.remote.api.auth.AuthService
import ru.tech.cookhelper.data.remote.api.chat.ChatApi
import ru.tech.cookhelper.data.remote.webSocket.message.MessageService
import ru.tech.cookhelper.data.repository.MessageRepositoryImpl
import ru.tech.cookhelper.data.repository.SettingsRepositoryImpl
import ru.tech.cookhelper.data.repository.UserRepositoryImpl
import ru.tech.cookhelper.data.utils.MoshiParser
import ru.tech.cookhelper.domain.repository.MessageRepository
import ru.tech.cookhelper.domain.repository.SettingsRepository
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(
        db: CookHelperDatabase
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsDao = db.settingsDao)

    @Provides
    @Singleton
    fun provideUserRepository(
        authService: AuthService,
        db: CookHelperDatabase
    ): UserRepository = UserRepositoryImpl(authService, db.userDao)

    @Provides
    @Singleton
    fun provideMessageRepository(
        messageService: MessageService,
        chatApi: ChatApi
    ): MessageRepository = MessageRepositoryImpl(
        jsonParser = MoshiParser(Moshi.Builder().build()),
        messageService = messageService,
        chatApi = chatApi
    )

}