package ru.tech.cookhelper.data.remote.api.user

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import ru.tech.cookhelper.data.remote.dto.PostDto
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.utils.Response

interface UserApi {

    @GET("api/user/get/feed/")
    suspend fun getFeed(
        @Query("token") token: String
    ): Result<Response<List<RecipePostDto>>>

    @Multipart
    @POST("api/feed/post/create/")
    fun createPost(
        @Part("token") token: String,
        @Part("label") label: String,
        @Part("text") text: String,
        @Part image: MultipartBody.Part?
    ): Call<Response<PostDto>>

}