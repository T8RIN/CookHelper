package ru.tech.cookhelper.presentation.ui.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class ViewModelEventsImpl<T> : ViewModelEvents<T> {
    private val eventChannel: Channel<T> = Channel(Channel.BUFFERED)
    override val eventFlow: Flow<T> = eventChannel.receiveAsFlow()
    override fun sendEvent(event: T) = eventChannel.trySend(event).isSuccess
}