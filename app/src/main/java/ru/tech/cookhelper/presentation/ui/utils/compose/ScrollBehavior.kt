package ru.tech.cookhelper.presentation.ui.utils.compose

sealed class ScrollBehavior {
    object Pinned : ScrollBehavior()
    object EnterAlways : ScrollBehavior()
    object ExitUntilCollapsed : ScrollBehavior()
}