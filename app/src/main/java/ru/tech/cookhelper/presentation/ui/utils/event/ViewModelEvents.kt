package ru.tech.cookhelper.presentation.ui.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface ViewModelEvents<T> {
    val eventChannel: Channel<T>
    val eventFlow: Flow<T>
    fun sendEvent(event: T)
}