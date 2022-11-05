package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Product(
    val id: Int,
    val title: String,
    val category: String,
    val mimetype: String
): Domain
