package ru.tech.cookhelper.presentation.ui.utils.event

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
inline fun <reified T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    var job by remember { mutableStateOf<Job?>(null) }
    LaunchedEffect(Unit) {
        job = lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(
                lifecycle = lifecycleOwner.lifecycle,
                minActiveState = minActiveState
            ).collect(collector = action)
        }
    }
    DisposableEffect(Unit) {
        onDispose { job?.cancel() }
    }
}