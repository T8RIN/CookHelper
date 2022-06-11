package ru.tech.cookhelper.data.remote.api.auth

import retrofit2.Call
import retrofit2.http.*

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

    @GET("api/user/get/verification/")
    suspend fun requestCode(
        @Query("token") token: String
    ): AuthInfo

}