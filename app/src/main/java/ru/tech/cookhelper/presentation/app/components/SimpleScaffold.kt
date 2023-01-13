package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleScaffold(
    modifier: Modifier = Modifier,
    topAppBar: (@Composable () -> Unit)? = null,
    bottomAppBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        topAppBar?.invoke()
        content()
        bottomAppBar?.invoke()
    }
}