package ru.tech.cookhelper.presentation.ui.utils.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.popAll
import ru.tech.cookhelper.core.utils.ReflectionUtils.name
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import dev.olshevski.navigation.reimagined.navigate as libNavigate

val LocalScreenController = compositionLocalOf<NavController<Screen>> {
    error("ScreenController not present")
}

inline val <T> T.isCurrentDestination: Boolean
    @ReadOnlyComposable
    @Composable
    get() = LocalScreenController.current.currentDestination == this

inline val <T> NavController<T>.currentDestination: T? get() = this.backstack.entries.lastOrNull()?.destination

fun <T : Any> NavController<T>.navigate(destination: T) = apply {
    if ((currentDestination ?: "")::class.name != destination::class.name) libNavigate(destination)
}

fun <T : Any> NavController<T>.navigateAndPopAll(destination: T) = apply {
    if ((currentDestination ?: "")::class.name != destination::class.name) {
        popAll()
        libNavigate(destination)
    }
}

fun <T> NavController<T>.goBack(): Boolean {
    if (backstack.entries.size == 1) return false
    return pop()
}