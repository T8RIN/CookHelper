package ru.tech.cookhelper.core.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitUtils {

    fun Retrofit.Builder.addLogger(
        level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    ): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(level)

        return client(httpClient.addInterceptor(logging).build())
    }

}