package ru.tech.cookhelper.presentation.topic_creation.components

import ru.tech.cookhelper.domain.model.Topic

data class TopicCreationState(
    val isLoading: Boolean = false,
    val topic: Topic? = null
)