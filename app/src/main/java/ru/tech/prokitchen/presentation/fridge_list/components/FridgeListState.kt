package ru.tech.prokitchen.presentation.fridge_list.components

import ru.tech.prokitchen.domain.model.Product

data class FridgeListState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null,
    val error: String = ""
)