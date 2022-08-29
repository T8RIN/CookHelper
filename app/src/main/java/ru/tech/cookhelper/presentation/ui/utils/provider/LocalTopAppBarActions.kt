package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*
import dev.olshevski.navigation.reimagined.NavController
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

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

@Composable
fun clearActionsOnNavigate(
    screenController: NavController<Screen> = LocalScreenController.current,
    topAppBarActions: MutableState<@Composable (RowScope.() -> Unit)?> = LocalTopAppBarActions.current
) {
    LaunchedEffect(screenController.currentDestination) {
        topAppBarActions.clearActions()
    }
}

fun MutableState<(@Composable RowScope.() -> Unit)?>.clearActions() {
    value = null
}