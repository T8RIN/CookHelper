package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHost = compositionLocalOf { SnackbarHostState() }