package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

object TopAppBarUtils {

    /**
     * Returns a [TopAppBarScrollBehavior]. A top app bar that is set up with this
     *
     * @param state the state object to be used to control or observe the top app bar's scroll
     * state. See [rememberTopAppBarState] for a state that is remembered across compositions.
     * @param canScroll a callback used to determine whether scroll events are to be
     * handled by this [EnterAlwaysScrollBehavior]
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun topAppBarScrollBehavior(
        scrollBehavior: ScrollBehavior = ScrollBehavior.Pinned,
        canScroll: () -> Boolean = { true },
        state: TopAppBarState = rememberTopAppBarState()
    ): TopAppBarScrollBehavior = when (scrollBehavior) {
        is ScrollBehavior.EnterAlways -> {
            TopAppBarDefaults.enterAlwaysScrollBehavior(
                state = state,
                canScroll = canScroll,
                snapAnimationSpec = scrollBehavior.snapAnimationSpec,
                flingAnimationSpec = scrollBehavior.flingAnimationSpec()
            )
        }
        is ScrollBehavior.ExitUntilCollapsed -> {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                state = state,
                canScroll = canScroll,
                snapAnimationSpec = scrollBehavior.snapAnimationSpec,
                flingAnimationSpec = scrollBehavior.flingAnimationSpec()
            )
        }
        is ScrollBehavior.Pinned -> {
            TopAppBarDefaults.pinnedScrollBehavior(
                state = state,
                canScroll = canScroll
            )
        }
    }

}