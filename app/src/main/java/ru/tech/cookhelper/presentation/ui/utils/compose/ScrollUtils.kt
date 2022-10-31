package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*

object ScrollUtils {

    /**
     * Returns whether the scrolling object is currently scrolling up.
     */
    @Composable
    fun ScrollState.isScrollingUp(): Boolean {
        var previousScrollOffset by remember(this) { mutableStateOf(value) }
        return remember(this) {
            derivedStateOf {
                (previousScrollOffset >= value).also {
                    previousScrollOffset = value
                }
            }
        }.value
    }

    /**
     * Returns whether the lazy list is currently scrolling up.
     */
    @Composable
    fun LazyListState.isScrollingUp(): Boolean {
        var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
        var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
        return remember(this) {
            derivedStateOf {
                if (previousIndex != firstVisibleItemIndex) {
                    previousIndex > firstVisibleItemIndex
                } else {
                    previousScrollOffset >= firstVisibleItemScrollOffset
                }.also {
                    previousIndex = firstVisibleItemIndex
                    previousScrollOffset = firstVisibleItemScrollOffset
                }
            }
        }.value
    }

    @Composable
    fun LazyListState.isLastItemVisible(): Boolean {
        return remember(this) {
            derivedStateOf {
                layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
            }
        }.value
    }
}