package ru.tech.cookhelper.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.tech.cookhelper.data.remote.dto.ProductDto
import ru.tech.cookhelper.data.remote.dto.RecipeDto

interface CookHelperApi {

    @GET("/api/get/cuisine")
    suspend fun getCuisine(): List<RecipeDto>

    @GET("/api/get/cuisine/{id}")
    suspend fun getDishById(@Path("id") id: Int): RecipeDto

    @GET("/api/get/products")
    suspend fun getProducts(): List<ProductDto>

    @GET("/api/get/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

}