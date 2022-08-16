package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navController
import ru.tech.cookhelper.presentation.ui.utils.Screen

val LocalScreenController = compositionLocalOf { navController<Screen>(Screen.Home.Recipes) }

inline val <T> T.isCurrentDestination: Boolean
    @ReadOnlyComposable
    @Composable
    get() = LocalScreenController.current.currentDestination == this

inline val <T> NavController<T>.currentDestination: T? get() = this.backstack.entries.lastOrNull()?.destination