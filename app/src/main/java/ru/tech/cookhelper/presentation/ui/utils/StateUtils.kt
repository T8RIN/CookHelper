package ru.tech.cookhelper.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

object StateUtils {
    @Composable
    fun <T> computedStateOf(
        calculation: () -> T
    ): State<T> = remember { derivedStateOf(calculation) }
}