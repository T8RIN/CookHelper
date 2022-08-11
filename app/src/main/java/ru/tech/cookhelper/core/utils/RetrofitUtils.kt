package ru.tech.cookhelper.core.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object RetrofitUtils {

    fun Retrofit.Builder.addLogger(
        level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    ): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(level)

        return client(httpClient.addInterceptor(logging).build())
    }

    fun Retrofit.Builder.setTimeout(
        timeout: Long = 60,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Retrofit.Builder = client(
        OkHttpClient.Builder()
            .setTimeout(timeout, timeUnit)
            .build()
    )

    fun OkHttpClient.Builder.setTimeout(
        timeout: Long = 60,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): OkHttpClient.Builder = readTimeout(timeout, timeUnit)
        .connectTimeout(timeout, timeUnit)

}