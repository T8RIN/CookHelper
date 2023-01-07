package ru.tech.cookhelper.presentation.ui.utils.event

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

interface ViewModelEvents<T> {
    val eventFlow: Flow<T>
    fun sendEvent(event: T): Boolean
}

@Composable
inline fun <reified T> ViewModelEvents<T>.collectEvents(
    noinline eventCollector: suspend (event: T) -> Unit
) = eventFlow.collectWithLifecycle(action = eventCollector)