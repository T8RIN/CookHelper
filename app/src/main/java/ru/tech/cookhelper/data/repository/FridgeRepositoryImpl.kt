package ru.tech.cookhelper.data.repository

import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.core.constants.Constants.DELIMITER
import ru.tech.cookhelper.core.constants.Status
import ru.tech.cookhelper.core.utils.kotlin.getOrExceptionAndNull
import ru.tech.cookhelper.data.remote.api.ingredients.IngredientsApi
import ru.tech.cookhelper.data.remote.dto.RecipePostDto
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.FridgeRepository
import ru.tech.cookhelper.presentation.ui.utils.toAction
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(
    private val ingredientsApi: IngredientsApi
) : FridgeRepository {

    override suspend fun getAvailableProducts(): Action<List<Product>> {
        ingredientsApi
            .getAvailableProducts()
            .getOrExceptionAndNull {
                return it.toAction()
            }?.let { response ->
                return when (response.status) {
                    Status.SUCCESS -> Action.Success(data = response.data?.map { it.asDomain() })
                    else -> Action.Empty(status = response.status)
                }
            }
        return Action.Empty()
    }

    override suspend fun addProductsToFridge(
        token: String,
        fridge: List<Product>
    ): Action<User> {
        ingredientsApi
            .addProductsToFridge(token, fridge.joinToString(DELIMITER) { it.id.toString() })
            .getOrExceptionAndNull {
                return it.toAction()
            }?.let { response ->
                return when (response.status) {
                    Status.SUCCESS -> Action.Success(data = response.data?.asDomain())
                    else -> Action.Empty(status = response.status)
                }
            }
        return Action.Empty()
    }

    override suspend fun getMatchedRecipes(token: String): Action<List<RecipePostDto>> {
        TODO("Not yet implemented")
    }

}