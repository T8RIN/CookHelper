package ru.tech.cookhelper.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.Setting

interface ProKitchenRepository {

    fun getCuisineList(): Flow<Action<List<Recipe>>>

    fun getDishById(id: Int): Flow<Action<Recipe>>

    suspend fun updateFav(id: Int, fav: Boolean)

    fun getFavouriteRecipes(): Flow<Action<List<Recipe>>>

    suspend fun checkFavoriteId(id: Int): Boolean

    fun getFridgeList(): Flow<Action<List<Product>>>

    suspend fun updateProduct(id: Int, inFridge: Boolean)

    fun getAllProducts(): Flow<Action<List<Product>>>

    fun getSettings(): Flow<List<Setting>>

    suspend fun insertSetting(id: Int, option: String)

}