package ru.tech.prokitchen.presentation.ui.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun showSnackbar(
    scope: CoroutineScope,
    host: SnackbarHostState,
    message: String,
    action: String,
    result: (SnackbarResult) -> Unit
) {
    scope.launch {
        result(
            host.showSnackbar(
                message = message,
                actionLabel = action
            )
        )
    }
}
