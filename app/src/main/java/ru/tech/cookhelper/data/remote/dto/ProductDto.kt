package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.Product

data class ProductDto(
    val id: Int,
    val title: String,
    val category: Int,
    val mimetype: String
) : Dto {
    override fun asDomain(): Product = Product(
        id = id, title = title, category = category, mimetype = mimetype
    )
}