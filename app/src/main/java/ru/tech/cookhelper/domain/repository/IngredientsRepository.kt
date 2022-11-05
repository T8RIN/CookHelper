package ru.tech.cookhelper.domain.repository

import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Product

interface IngredientsRepository {
    suspend fun getAvailableProducts(): Action<List<Product>>
}