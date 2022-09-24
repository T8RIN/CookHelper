package ru.tech.cookhelper.presentation.feed.components

import ru.tech.cookhelper.domain.model.RecipePost

data class FeedState(
    val data: List<RecipePost> = emptyList(),
    val isLoading: Boolean = false,
)
