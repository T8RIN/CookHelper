package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.app.components.ToastDuration
import ru.tech.cookhelper.presentation.app.components.ToastHostState

fun ToastHostState.show(
    icon: ImageVector? = null,
    message: String,
    duration: ToastDuration = ToastDuration.Short,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
) = scope.launch { showToast(message, icon, duration) }