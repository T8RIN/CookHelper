package ru.tech.cookhelper.presentation.dishes_based_on_fridge.components

import ru.tech.cookhelper.domain.model.Recipe

data class PodborState(
    val isLoading: Boolean = false,
    val recipeList: List<Pair<Recipe, Int>>? = null,
    val error: String = ""
)