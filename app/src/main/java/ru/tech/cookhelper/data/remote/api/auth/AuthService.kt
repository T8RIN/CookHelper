package ru.tech.cookhelper.data.remote.api.auth

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {

    @Multipart
    @POST("api/user/post/auth/")
    fun loginWith(
        @Part("login") login: String,
        @Part("password") password: String
    ): Call<AuthInfo>

    @Multipart
    @POST("api/user/post/reg/")
    fun registerWith(
        @Part("name") name: String,
        @Part("surname") surname: String,
        @Part("nickname") nickname: String,
        @Part("email") email: String,
        @Part("password") password: String
    ): Call<AuthInfo>

    @Multipart
    @POST("api/user/post/verify/")
    fun verifyEmail(
        @Part("code") code: String,
        @Part("token") token: String
    ): Call<AuthInfo>

}

fun <T> Call<T>.start(
    onSuccess: (call: Call<T>, response: Response<T>) -> Unit,
    onError: (call: Call<T>, t: Throwable) -> Unit,
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = onSuccess(call, response)
        override fun onFailure(call: Call<T>, t: Throwable) = onError(call, t)
    })
}
