package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable

object TopAppBarUtils {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun topAppBarScrollBehavior(
        scrollBehavior: ScrollBehavior = ScrollBehavior.Pinned,
        canScroll: () -> Boolean = { true },
        topAppBarState: TopAppBarState = rememberTopAppBarState()
    ) = when (scrollBehavior) {
        ScrollBehavior.EnterAlways -> {
            TopAppBarDefaults.enterAlwaysScrollBehavior(
                state = topAppBarState,
                canScroll = canScroll
            )
        }
        is ScrollBehavior.ExitUntilCollapsed -> {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                state = topAppBarState,
                canScroll = canScroll
            )
        }
        ScrollBehavior.Pinned -> {
            TopAppBarDefaults.pinnedScrollBehavior(
                state = topAppBarState,
                canScroll = canScroll
            )
        }
    }

}