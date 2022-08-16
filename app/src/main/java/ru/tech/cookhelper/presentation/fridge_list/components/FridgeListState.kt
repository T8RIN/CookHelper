package ru.tech.cookhelper.presentation.fridge_list.components

import ru.tech.cookhelper.domain.model.Product

data class FridgeListState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null
)