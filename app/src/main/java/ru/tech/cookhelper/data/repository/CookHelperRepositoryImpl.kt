package ru.tech.cookhelper.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.data.local.dao.FavRecipeDao
import ru.tech.cookhelper.data.local.dao.FridgeDao
import ru.tech.cookhelper.data.local.dao.SettingsDao
import ru.tech.cookhelper.data.local.entity.SettingsEntity
import ru.tech.cookhelper.data.local.entity.toSetting
import ru.tech.cookhelper.data.remote.api.CookHelperApi
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.Setting
import ru.tech.cookhelper.domain.repository.CookHelperRepository
import javax.inject.Inject

class CookHelperRepositoryImpl @Inject constructor(
    private val api: CookHelperApi,
    private val favRecipeDao: FavRecipeDao,
    private val fridgeDao: FridgeDao,
    private val settingsDao: SettingsDao
) : CookHelperRepository {

    //TODO: Fix this Shit

    override fun getCuisineList(): Flow<Action<List<Recipe>>> = flow {
//        emit(Action.Loading())
//        try {
//            val remoteCoins = api.getCuisine()
//            emit(Action.Success(remoteCoins.map { it.toRecipe() }))
//        } catch (e: Exception) {
//            emit(Action.Error(e.localizedMessage))
//        }
    }

    override fun getDishById(id: Int): Flow<Action<Recipe>> = flow {
//        emit(Action.Loading())
//        try {
//            val remoteData = api.getDishById(id)
//            emit(Action.Success(remoteData.toRecipe()))
//        } catch (e: Exception) {
//            emit(Action.Error(e.localizedMessage))
//        }
    }

    override suspend fun updateFav(id: Int, fav: Boolean) {
//        if (!fav) {
//            favRecipeDao.insertRecipe(FavRecipeEntity(id))
//        } else {
//            favRecipeDao.deleteRecipe(id)
//        }
    }

    override fun getFavouriteRecipes(): Flow<Action<List<Recipe>>> = flow {

//        favRecipeDao.getFavRecipes().collect { list ->
//            emit(Action.Loading())
//
//            val ids = list.map { it.id }
//            val data: List<Recipe> = ids.mapNotNull { id ->
//                try {
//                    api.getDishById(id).toRecipe()
//                } catch (e: Exception) {
//                    null
//                }
//            }
//            if (data.isNotEmpty()) emit(Action.Success(data))
//            else emit(Action.Empty())
//        }

    }

    override suspend fun checkFavoriteId(id: Int): Boolean {
//        return favRecipeDao.getById(id)?.let { true } ?: false
        return true
    }

    override fun getFridgeList(): Flow<Action<List<Product>>> = flow {
//        fridgeDao.getFridgeList().collect { list ->
//            emit(Action.Loading())
//
//            val ids = list.map { it.id }
//            val data: List<Product> = ids.mapNotNull { id ->
//                try {
//                    api.getProductById(id).toProduct()
//                } catch (e: Exception) {
//                    null
//                }
//            }
//            if (data.isNotEmpty()) emit(Action.Success(data))
//            else emit(Action.Empty())
//        }
    }

    override suspend fun updateProduct(id: Int, inFridge: Boolean) {
//        if (!inFridge) {
//            fridgeDao.insertProduct(ProductEntity(id))
//        } else {
//            fridgeDao.removeProduct(id)
//        }
    }

    override fun getAllProducts(): Flow<Action<List<Product>>> = flow {
//        emit(Action.Loading())
//        try {
//            val products = api.getProducts()
//            emit(Action.Success(products.map { it.toProduct() }))
//        } catch (e: Exception) {
//            emit(Action.Error(e.localizedMessage))
//        }
    }

    override fun getSettings(): Flow<List<Setting>> =
        settingsDao.getSettings()
            .map { settingsList ->
                settingsList.map { entity -> entity.toSetting() }
            }

    override suspend fun insertSetting(id: Int, option: String) {
        settingsDao.insertSetting(SettingsEntity(id, option))
    }

}