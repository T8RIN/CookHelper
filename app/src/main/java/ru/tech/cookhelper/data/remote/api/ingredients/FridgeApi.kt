package ru.tech.cookhelper.data.remote.api.ingredients

import retrofit2.http.*
import ru.tech.cookhelper.data.remote.dto.MatchedRecipeDto
import ru.tech.cookhelper.data.remote.dto.ProductDto
import ru.tech.cookhelper.data.remote.dto.UserDto
import ru.tech.cookhelper.data.remote.utils.Response

interface FridgeApi {

    @GET("api/ingredient/get/all/")
    suspend fun getAvailableProducts(): Result<Response<List<ProductDto>>>

    @Multipart
    @POST("api/user/post/fridge/insert/")
    suspend fun addProductsToFridge(
        @Part("token") token: String,
        @Part("fridge") fridge: String
    ): Result<Response<UserDto>>

    @GET("api/user/get/fridge/recipe/")
    suspend fun getMatchedRecipes(
        @Query("token") token: String
    ): Result<Response<List<MatchedRecipeDto>>>

}