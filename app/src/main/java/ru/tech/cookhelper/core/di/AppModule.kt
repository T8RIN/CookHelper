package ru.tech.cookhelper.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tech.cookhelper.core.constants.Constants.BASE_URL
import ru.tech.cookhelper.data.local.ProKitchenDatabase
import ru.tech.cookhelper.data.remote.api.ProKitchenApi
import ru.tech.cookhelper.data.repository.ProKitchenRepositoryImpl
import ru.tech.cookhelper.domain.repository.ProKitchenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProKitchenApi(): ProKitchenApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ProKitchenApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): ProKitchenDatabase = Room.databaseBuilder(
        applicationContext,
        ProKitchenDatabase::class.java,
        "kastybiy_db"
    ).fallbackToDestructiveMigration().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideProKitchenRepository(
        api: ProKitchenApi,
        db: ProKitchenDatabase
    ): ProKitchenRepository =
        ProKitchenRepositoryImpl(api, db.favRecipeDao, db.fridgeDao, db.settingsDao)

}