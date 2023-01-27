package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import dev.olshevski.navigation.reimagined.NavTransitionSpec

@OptIn(ExperimentalAnimationApi::class)
val ScaleCrossfadeTransitionSpec = NavTransitionSpec<Any?> { _, _, _ ->
    (fadeIn(tween(500)) + scaleIn(initialScale = 0.9f, animationSpec = tween(500)))
        .with(fadeOut(tween(300)) + scaleOut(targetScale = 0.9f, animationSpec = tween(300)))
}