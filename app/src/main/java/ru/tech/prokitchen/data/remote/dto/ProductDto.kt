package ru.tech.prokitchen.data.remote.dto

import ru.tech.prokitchen.domain.model.Product

data class ProductDto(
    val id: Int,
    val name: String
)

fun ProductDto.toProduct() = Product(id, name)