package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

val LocalSnackbarHost = compositionLocalOf { SnackbarHostState() }

@Composable
fun rememberSnackbarHostState() = remember { SnackbarHostState() }