package ru.tech.cookhelper.core.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitUtils {

    fun Retrofit.Builder.addLogger(
        level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    ): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(level)

        return client(httpClient.addInterceptor(logging).build())
    }

}