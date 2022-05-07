package ru.tech.prokitchen.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.tech.prokitchen.data.remote.dto.ProductDto
import ru.tech.prokitchen.data.remote.dto.RecipeDto

interface ProKitchenApi {

    @GET("/api/get/cuisine")
    suspend fun getCuisine(): List<RecipeDto>

    @GET("/api/get/cuisine/{id}")
    suspend fun getDishById(@Path("id") id: Int): RecipeDto

    @GET("/api/get/products")
    suspend fun getProducts(): List<ProductDto>

    @GET("/api/get/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

}