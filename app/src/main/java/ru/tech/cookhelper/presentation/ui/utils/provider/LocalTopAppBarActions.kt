package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*

val LocalTopAppBarActions = compositionLocalOf<MutableState<TopAppBarActions?>> {
    error("TopAppBarActionsState not present")
}

@Composable
operator fun TopAppBarActionsState.invoke(rowScope: RowScope) {
    this.value?.invoke(rowScope)
}

@SuppressLint("ComposableNaming")
@Composable
fun TopAppBarActionsState.setActions(actions: TopAppBarActions?) {
    LaunchedEffect(Unit) { value = actions }
}

fun TopAppBarActionsState.clearActions() {
    value = null
}

typealias TopAppBarActions = @Composable RowScope.() -> Unit
typealias TopAppBarActionsState = MutableState<TopAppBarActions?>

@Composable
fun rememberTopAppBarActions(
    actions: TopAppBarActions? = null
) = remember { mutableStateOf(actions) }