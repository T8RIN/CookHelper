package ru.tech.cookhelper.data.remote.api.ingredients

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.tech.cookhelper.data.remote.dto.ProductDto
import ru.tech.cookhelper.data.remote.utils.Response
import ru.tech.cookhelper.domain.model.Product

interface IngredientsApi {

    @GET("api/ingredient/get/all/")
    suspend fun getAvailableProducts(): Result<Response<List<ProductDto>>>

    @POST("api/user/post/fridge/insert/")
    suspend fun addProductsToFridge(
        @Query("token") token: String,
        @Query("fridge") fridge: List<Product>
    ): Result<Response<Boolean>>

}