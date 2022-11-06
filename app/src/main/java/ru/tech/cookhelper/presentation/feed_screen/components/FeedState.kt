package ru.tech.cookhelper.presentation.feed_screen.components

import ru.tech.cookhelper.domain.model.Recipe

data class FeedState(
    val data: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
)
