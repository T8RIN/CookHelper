package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.*
import ru.tech.cookhelper.presentation.ui.utils.Screen

val LocalScreenController = compositionLocalOf { mutableStateOf<Screen>(Screen.Home) }


fun MutableState<Screen>.navigate(screen: Screen) {
    this.value = screen
}

val MutableState<Screen>.currentScreen: Screen get() = this.value

val Screen.isCurrentScreen: Boolean
    @ReadOnlyComposable
    @Composable
    get() = LocalScreenController.current.currentScreen == this