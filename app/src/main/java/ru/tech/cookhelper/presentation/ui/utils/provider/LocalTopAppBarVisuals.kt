package ru.tech.cookhelper.presentation.ui.utils.provider

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*

typealias TopAppBarNavigationIcon = @Composable () -> Unit
typealias TopAppBarActions = @Composable RowScope.() -> Unit
typealias TopAppBarTitle = @Composable () -> Unit
typealias TopAppBarNavigationIconState = MutableState<TopAppBarNavigationIcon?>
typealias TopAppBarActionsState = MutableState<TopAppBarActions?>
typealias TopAppBarTitleState = MutableState<TopAppBarTitle?>

val LocalTopAppBarVisuals = compositionLocalOf<TopAppBarVisuals> {
    error("TopAppBarVisuals not present")
}

data class TopAppBarVisuals(
    private val topAppBarActionsState: TopAppBarActionsState,
    private val topAppBarTitleState: TopAppBarTitleState,
    private val topAppBarNavigationIconState: TopAppBarNavigationIconState
) {
    val actions: TopAppBarActions?
        get() = topAppBarActionsState.value

    val title: TopAppBarTitle?
        get() = topAppBarTitleState.value

    val navigationIcon: TopAppBarNavigationIcon?
        get() = topAppBarNavigationIconState.value

    fun clear() {
        topAppBarActionsState.value = null
        topAppBarTitleState.value = null
        topAppBarNavigationIconState.value = null
    }

    fun clearTitle() {
        topAppBarTitleState.value = null
    }

    fun clearActions() {
        topAppBarActionsState.value = null
    }

    fun clearNavigationIcon() {
        topAppBarNavigationIconState.value = null
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun update(
        builder: @Composable TopAppBarVisualsScope.() -> Unit
    ) = builder(TopAppBarVisualsScopeImpl(this))


    @SuppressLint("ComposableNaming")
    private class TopAppBarVisualsScopeImpl(
        private val topAppBarVisuals: TopAppBarVisuals
    ) : TopAppBarVisualsScope {

        @Composable
        override fun actions(builder: TopAppBarActions?) {
            LaunchedEffect(Unit) {
                topAppBarVisuals.topAppBarActionsState.value = builder
            }
        }

        @Composable
        override fun title(builder: TopAppBarTitle?) {
            LaunchedEffect(Unit) {
                topAppBarVisuals.topAppBarTitleState.value = builder
            }
        }

        @Composable
        override fun navIcon(builder: TopAppBarNavigationIcon?) {
            LaunchedEffect(Unit) {
                topAppBarVisuals.topAppBarNavigationIconState.value = builder
            }
        }

    }

    companion object {

        @Composable
        fun rememberTopAppBarVisuals(
            topAppBarActionsState: TopAppBarActionsState = rememberTopAppBarActions(),
            topAppBarTitleState: TopAppBarTitleState = rememberTopAppBarTitle(),
            topAppBarNavigationIconState: TopAppBarNavigationIconState = rememberTopAppBarNavigationIcon()
        ): TopAppBarVisuals = TopAppBarVisuals(
            topAppBarActionsState = topAppBarActionsState,
            topAppBarTitleState = topAppBarTitleState,
            topAppBarNavigationIconState = topAppBarNavigationIconState
        )

        @Composable
        fun rememberTopAppBarActions(
            actions: TopAppBarActions? = null
        ) = remember { mutableStateOf(actions) }

        @Composable
        fun rememberTopAppBarNavigationIcon(
            navigationIcon: TopAppBarNavigationIcon? = null
        ) = remember { mutableStateOf(navigationIcon) }

        @Composable
        fun rememberTopAppBarTitle(
            title: TopAppBarTitle? = null
        ) = remember { mutableStateOf(title) }

    }

}


@SuppressLint("ComposableNaming")
interface TopAppBarVisualsScope {

    @Composable
    fun actions(builder: TopAppBarActions?)

    @Composable
    fun title(builder: TopAppBarTitle?)

    @Composable
    fun navIcon(builder: TopAppBarNavigationIcon?)

}