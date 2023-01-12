package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun SimpleScaffold(
    modifier: Modifier = Modifier,
    topAppBar: (@Composable () -> Unit)? = null,
    bottomAppBar: (@Composable () -> Unit)? = null,
    autoApplyPadding: Boolean = true,
    content: @Composable (PaddingValues?) -> Unit
) {
    val density = LocalDensity.current

    var topAppBarHeight by remember { mutableStateOf(0.dp) }
    var bottomAppBarHeight by remember { mutableStateOf(0.dp) }

    val paddingValues = remember(topAppBarHeight, bottomAppBarHeight) {
        PaddingValues(top = topAppBarHeight, bottom = bottomAppBarHeight)
    }

    Box(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onGloballyPositioned {
                    with(density) {
                        topAppBarHeight = it.size.height.toDp()
                    }
                },
            content = {
                topAppBar?.invoke()
            }
        )
        Box(modifier = if (autoApplyPadding) Modifier.padding(paddingValues) else Modifier) {
            content(if (autoApplyPadding) null else paddingValues)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    with(density) {
                        bottomAppBarHeight = it.size.height.toDp()
                    }
                },
            content = {
                bottomAppBar?.invoke()
            }
        )

    }
}