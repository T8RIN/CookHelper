package ru.tech.cookhelper.data.remote.api.user

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.data.remote.utils.Response

interface UserApi {

    @GET("/user/get/feed")
    suspend fun getFeed(
        @Query("token") token: String
    ): Result<Response<List<RecipePostDto>>>

}