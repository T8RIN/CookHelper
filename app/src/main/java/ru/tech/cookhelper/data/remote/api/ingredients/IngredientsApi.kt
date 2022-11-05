package ru.tech.cookhelper.data.remote.api.ingredients

import retrofit2.http.GET
import ru.tech.cookhelper.data.remote.dto.ProductDto
import ru.tech.cookhelper.data.remote.utils.Response

interface IngredientsApi {

    @GET("api/ingredient/get/all/")
    suspend fun getAvailableProducts(): Result<Response<List<ProductDto>>>

}