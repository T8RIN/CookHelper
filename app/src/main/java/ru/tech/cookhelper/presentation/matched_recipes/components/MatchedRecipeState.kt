package ru.tech.cookhelper.presentation.matched_recipes.components

import ru.tech.cookhelper.domain.model.Recipe

data class MatchedRecipeState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)
