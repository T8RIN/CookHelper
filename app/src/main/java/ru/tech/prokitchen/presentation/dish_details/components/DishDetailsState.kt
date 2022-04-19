package ru.tech.prokitchen.presentation.dish_details.components

import ru.tech.prokitchen.domain.model.Recipe

data class DishDetailsState(
    val isLoading: Boolean = false,
    val dish: Recipe? = null,
    val error: String = ""
)