package ru.tech.cookhelper.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tech.cookhelper.data.local.database.Database
import ru.tech.cookhelper.data.local.database.TypeConverters
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
        typeConverters: TypeConverters
    ): Database = Room.databaseBuilder(
        context = applicationContext,
        klass = Database::class.java,
        name = "CookHelperDatabase"
    ).addTypeConverter(typeConverters)
        .fallbackToDestructiveMigration()
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()

    @Singleton
    @Provides
    fun provideTypeConverters(
        jsonParser: JsonParser
    ): TypeConverters = TypeConverters(jsonParser)

}