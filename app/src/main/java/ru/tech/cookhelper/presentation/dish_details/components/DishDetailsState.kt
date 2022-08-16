package ru.tech.cookhelper.presentation.dish_details.components

import ru.tech.cookhelper.domain.model.Recipe

data class DishDetailsState(
    val isLoading: Boolean = false,
    val dish: Recipe? = null
)