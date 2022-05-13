package ru.tech.cookhelper.presentation.app.components

import ru.tech.cookhelper.domain.model.Product

data class ProductsListState(
    val isLoading: Boolean = false,
    val list: List<Product>? = null,
    val error: String = ""
)
