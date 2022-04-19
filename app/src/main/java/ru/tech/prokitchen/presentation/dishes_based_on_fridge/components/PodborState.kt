package ru.tech.prokitchen.presentation.dishes_based_on_fridge.components

import ru.tech.prokitchen.domain.model.Recipe

data class PodborState(
    val isLoading: Boolean = false,
    val recipeList: List<Pair<Recipe, Int>>? = null,
    val error: String = ""
)