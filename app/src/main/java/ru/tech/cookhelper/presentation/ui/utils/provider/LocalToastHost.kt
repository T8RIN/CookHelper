package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import ru.tech.cookhelper.presentation.app.components.ToastHostState

val LocalToastHost =
    compositionLocalOf<ToastHostState> { error("ToastData not present") }

@Composable
fun rememberToastHostState() = remember { ToastHostState() }