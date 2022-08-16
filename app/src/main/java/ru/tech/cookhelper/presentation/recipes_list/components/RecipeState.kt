package ru.tech.cookhelper.presentation.recipes_list.components

import ru.tech.cookhelper.domain.model.Recipe

data class RecipeState(
    val isLoading: Boolean = false,
    val recipeList: List<Recipe>? = null
)