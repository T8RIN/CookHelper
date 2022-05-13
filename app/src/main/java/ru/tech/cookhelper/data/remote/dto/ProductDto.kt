package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.domain.model.Product

data class ProductDto(
    val id: Int,
    val name: String
)

fun ProductDto.toProduct() = Product(id, name)