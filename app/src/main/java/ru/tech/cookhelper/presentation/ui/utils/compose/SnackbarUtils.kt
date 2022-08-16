package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SnackbarUtils {
    fun CoroutineScope.showSnackbar(
        host: SnackbarHostState,
        message: String,
        action: String = "",
        result: (SnackbarResult) -> Unit = {}
    ) {
        launch {
            result(
                host.showSnackbar(
                    message = message,
                    actionLabel = action
                )
            )
        }
    }

    inline val SnackbarResult.actionPerformed: Boolean
        get() {
            return this == SnackbarResult.ActionPerformed
        }

    inline val SnackbarResult.dismissed: Boolean
        get() {
            return this == SnackbarResult.Dismissed
        }
}