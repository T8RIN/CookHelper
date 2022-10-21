package ru.tech.cookhelper.presentation.post_creation.components

import ru.tech.cookhelper.domain.model.Post

data class PostCreationState(
    val isLoading: Boolean = false,
    val post: Post? = null
)
