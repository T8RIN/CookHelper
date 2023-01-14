package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun SimpleScaffold(
    modifier: Modifier = Modifier,
    topAppBar: (@Composable () -> Unit)? = null,
    bottomAppBar: (@Composable () -> Unit)? = null,
    appBarsPaddingProvider: ((PaddingValues) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    var topAppBarHeight by remember { mutableStateOf(0.dp) }
    var bottomAppBarHeight by remember { mutableStateOf(0.dp) }

    LaunchedEffect(topAppBarHeight, bottomAppBarHeight) {
        appBarsPaddingProvider?.invoke(
            PaddingValues(
                top = topAppBarHeight,
                bottom = bottomAppBarHeight
            )
        )
    }

    if (appBarsPaddingProvider != null) {
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
                    .onSizeChanged {
                        with(density) {
                            topAppBarHeight = it.height.toDp()
                        }
                    }
            ) { topAppBar?.invoke() }
            Box(
                modifier = Modifier.zIndex(0f),
            ) { content() }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(1f)
                    .onSizeChanged {
                        with(density) {
                            bottomAppBarHeight = it.height.toDp()
                        }
                    }
            ) { bottomAppBar?.invoke() }
        }
    } else {
        Column(modifier = modifier) {
            topAppBar?.invoke()
            content()
            bottomAppBar?.invoke()
        }
    }

}