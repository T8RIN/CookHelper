package ru.tech.prokitchen.presentation.app.components

import ru.tech.prokitchen.domain.model.Product

data class ProductsListState(
    val isLoading: Boolean = false,
    val list: List<Product>? = null,
    val error: String = ""
)
