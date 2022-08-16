package ru.tech.cookhelper.presentation.ui.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface ViewModelEvents {
    val eventChannel: Channel<Event>
    val eventFlow: Flow<Event>
    fun sendEvent(event: Event)
}