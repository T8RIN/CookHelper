package ru.tech.cookhelper.presentation.ui.utils.event

import kotlinx.coroutines.flow.Flow

interface ViewModelEvents<T> {
    val eventFlow: Flow<T>
    fun sendEvent(event: T)
}