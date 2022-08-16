package ru.tech.cookhelper.data.remote.api.auth

import retrofit2.Call
import retrofit2.http.*
import ru.tech.cookhelper.data.remote.dto.AuthInfo

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

    @GET("api/user/get/recover-password/")
    suspend fun requestPasswordRestoreCode(
        @Query("login") login: String
    ): AuthInfo

    @Multipart
    @POST("api/user/post/recover-password/")
    fun restorePasswordBy(
        @Part("login") login: String,
        @Part("code") code: String,
        @Part("password") password: String
    ): Call<AuthInfo>

    @GET("api/user/get/login-availability/")
    suspend fun checkLoginOrEmailForAvailability(
        @Query("login") query: String
    ): AuthInfo

}