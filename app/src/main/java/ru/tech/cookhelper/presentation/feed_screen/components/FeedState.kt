package ru.tech.cookhelper.presentation.feed_screen.components

import ru.tech.cookhelper.domain.model.RecipePost

data class FeedState(
    val data: List<RecipePost> = emptyList(),
    val isLoading: Boolean = false,
)
