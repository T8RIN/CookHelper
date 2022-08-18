package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*

val LocalTopAppBarActions = compositionLocalOf {
    mutableStateOf<(@Composable RowScope.() -> Unit)?>(null)
}

@Composable
operator fun MutableState<(@Composable RowScope.() -> Unit)?>.invoke(rowScope: RowScope) {
    this.value?.invoke(rowScope)
}

@SuppressLint("ComposableNaming")
@Composable
fun MutableState<(@Composable RowScope.() -> Unit)?>.setActions(actions: (@Composable RowScope.() -> Unit)?) {
    LaunchedEffect(Unit) { value = actions }
}

fun MutableState<(@Composable RowScope.() -> Unit)?>.clearActions() {
    value = null
}