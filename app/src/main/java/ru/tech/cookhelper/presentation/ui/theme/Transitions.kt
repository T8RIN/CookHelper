package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import dev.olshevski.navigation.reimagined.AnimatedNavHostTransitionSpec

@OptIn(ExperimentalAnimationApi::class)
val ScaleCrossfadeTransitionSpec = AnimatedNavHostTransitionSpec<Any?> { _, _, _ ->
    (fadeIn() + scaleIn(initialScale = 0.85f))
        .with(fadeOut(tween(durationMillis = 50)) + scaleOut(targetScale = 0.85f))
}