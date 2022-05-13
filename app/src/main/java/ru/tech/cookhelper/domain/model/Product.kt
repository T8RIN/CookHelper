package ru.tech.cookhelper.domain.model

data class Product(
    val id: Int,
    val name: String,
    val inFridge: Boolean = false
)
