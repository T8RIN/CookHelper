package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SnackbarUtils {

    fun SnackbarHostState.show(
        scope: CoroutineScope,
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        result: (SnackbarResult) -> Unit = {}
    ) {
        scope.launch {
            result(
                showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = duration
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