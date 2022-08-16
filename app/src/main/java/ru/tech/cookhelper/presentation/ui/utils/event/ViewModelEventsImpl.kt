package ru.tech.cookhelper.presentation.ui.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class ViewModelEventsImpl : ViewModelEvents {
    override val eventChannel: Channel<Event> = Channel(Channel.BUFFERED)
    override val eventFlow: Flow<Event> = eventChannel.receiveAsFlow()
    override fun sendEvent(event: Event) {
        eventChannel.trySend(event)
    }
}