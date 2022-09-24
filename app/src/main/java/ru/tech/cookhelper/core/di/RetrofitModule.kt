package ru.tech.cookhelper.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.core.utils.RetrofitUtils.setTimeout
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .let {
            val httpClient = OkHttpClient.Builder()
                .setTimeout(60, TimeUnit.SECONDS)
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient
                .addInterceptor(logging)
                .addInterceptor(RetryInterceptor { !it.isSuccessful })
            return@let it.client(httpClient.build())
        }
        .build()

}

class RetryInterceptor(
    private val reason: (Response) -> Boolean
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        while (reason(response) && tryCount < 3) {
            tryCount++
            response.close()
            response = chain.proceed(request)
        }
        return response
    }
}