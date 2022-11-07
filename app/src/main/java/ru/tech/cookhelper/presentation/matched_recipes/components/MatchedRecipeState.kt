package ru.tech.cookhelper.presentation.matched_recipes.components

import ru.tech.cookhelper.domain.model.MatchedRecipe

data class MatchedRecipeState(
    val isLoading: Boolean = false,
    val recipes: List<MatchedRecipe> = emptyList()
)
