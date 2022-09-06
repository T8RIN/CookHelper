package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*

val LocalTopAppBarActions = compositionLocalOf {
    mutableStateOf<TopAppBarActions?>(null)
}

@Composable
operator fun MutableState<TopAppBarActions?>.invoke(rowScope: RowScope) {
    this.value?.invoke(rowScope)
}

@SuppressLint("ComposableNaming")
@Composable
fun MutableState<TopAppBarActions?>.setActions(actions: TopAppBarActions?) {
    LaunchedEffect(Unit) { value = actions }
}

fun MutableState<TopAppBarActions?>.clearActions() {
    value = null
}

typealias TopAppBarActions = @Composable RowScope.() -> Unit

@Composable
fun rememberTopAppBarActions(
    actions: TopAppBarActions? = null
) = remember { mutableStateOf(actions) }