package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.*


val LocalTopAppBarTitle = compositionLocalOf<TopAppBarTitleState> {
    error("TopAppBarTitleState not present")
}

@SuppressLint("ComposableNaming")
@Composable
fun TopAppBarTitleState.setTitle(title: TopAppBarTitle?) {
    LaunchedEffect(Unit) { value = title }
}

fun TopAppBarTitleState.clearTitle() {
    value = null
}

typealias TopAppBarTitle = @Composable () -> Unit
typealias TopAppBarTitleState = MutableState<TopAppBarTitle?>

@Composable
fun rememberTopAppBarTitle(
    title: TopAppBarTitle? = null
) = remember { mutableStateOf(title) }