package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

object TopAppBarStateUtils {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun rememberTopAppBarScrollBehavior(
        scrollBehavior: ScrollBehavior = ScrollBehavior.Pinned,
        canScroll: () -> Boolean = { true },
        topAppBarState: TopAppBarState = rememberTopAppBarState()
    ) = remember {
        mutableStateOf(
            when (scrollBehavior) {
                ScrollBehavior.EnterAlways -> {
                    TopAppBarDefaults.enterAlwaysScrollBehavior(
                        state = topAppBarState,
                        canScroll = canScroll
                    )
                }
                is ScrollBehavior.ExitUntilCollapsed -> {
                    TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                        decayAnimationSpec = scrollBehavior.decayAnimationSpec,
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
        )
    }
}