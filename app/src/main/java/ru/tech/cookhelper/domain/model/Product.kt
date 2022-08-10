package ru.tech.cookhelper.domain.model

data class Product(
    val id: Int,
    val name: String,
    val amount: Float = 0.0f,
    val mimeType: String = "",
    val inFridge: Boolean = false
)
