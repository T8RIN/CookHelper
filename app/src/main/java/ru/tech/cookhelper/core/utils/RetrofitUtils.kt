package ru.tech.cookhelper.core.utils

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit


object RetrofitUtils {

    fun File?.toMultipartFormData(filename: String = this?.name ?: ""): MultipartBody.Part? =
        this?.let {
            MultipartBody.Part.createFormData(
                name = "image",
                filename = filename,
                body = it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        }

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

    fun <T> retrofit2.Response<T>.bodyOrThrow(): T =
        body() ?: throw Throwable("${code()} ${message()}")

    class RetryInterceptor(
        private val tryCount: Int = 3,
        private val reason: (Response) -> Boolean
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            var response = chain.proceed(request)

            var tryCount = 0
            while (reason(response) && tryCount < this.tryCount) {
                tryCount++
                response.close()
                response = chain.proceed(request)
            }
            return response
        }
    }

}