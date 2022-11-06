package ru.tech.cookhelper.domain.repository

import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.User

interface FridgeRepository {
    suspend fun getAvailableProducts(): Action<List<Product>>
    suspend fun addProductsToFridge(token: String, fridge: List<Product>): Action<User>
    suspend fun getMatchedRecipes(token: String): Action<List<Recipe>>
}