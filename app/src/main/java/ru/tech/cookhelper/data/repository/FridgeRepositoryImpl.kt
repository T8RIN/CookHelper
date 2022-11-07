package ru.tech.cookhelper.data.repository

import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.core.constants.Constants.DELIMITER
import ru.tech.cookhelper.core.constants.Status
import ru.tech.cookhelper.core.utils.kotlin.getOrExceptionAndNull
import ru.tech.cookhelper.data.remote.api.ingredients.FridgeApi
import ru.tech.cookhelper.domain.model.MatchedRecipe
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.FridgeRepository
import ru.tech.cookhelper.presentation.ui.utils.toAction
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(
    private val fridgeApi: FridgeApi
) : FridgeRepository {

    override suspend fun getAvailableProducts(): Action<List<Product>> {
        fridgeApi
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
        fridgeApi
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

    override suspend fun getMatchedRecipes(token: String): Action<List<MatchedRecipe>> {
        fridgeApi
            .getMatchedRecipes(token)
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

    override suspend fun removeProductsFromFridge(
        token: String,
        fridge: List<Product>
    ): Action<User> {
        fridgeApi
            .removeProductsFromFridge(token, fridge.joinToString(DELIMITER) { it.id.toString() })
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

}