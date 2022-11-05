package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.*

val LocalTopAppBarNavigationIcon = compositionLocalOf<TopAppBarNavigationIconState> {
    error("TopAppBarNavigationIconState not present")
}

@Composable
operator fun TopAppBarNavigationIconState.invoke() = this.value?.invoke()

@SuppressLint("ComposableNaming")
@Composable
fun TopAppBarNavigationIconState.setNavigationIcon(navigationIcon: TopAppBarNavigationIcon?) {
    LaunchedEffect(Unit) { value = navigationIcon }
}

fun TopAppBarNavigationIconState.clearNavigationIcon() {
    value = null
}

typealias TopAppBarNavigationIcon = @Composable () -> Unit
typealias TopAppBarNavigationIconState = MutableState<TopAppBarNavigationIcon?>

@Composable
fun rememberTopAppBarNavigationIcon(
    navigationIcon: TopAppBarNavigationIcon? = null
) = remember { mutableStateOf(navigationIcon) }