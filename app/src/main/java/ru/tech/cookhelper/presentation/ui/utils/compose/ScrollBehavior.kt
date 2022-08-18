package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.animation.core.DecayAnimationSpec

sealed class ScrollBehavior {
    object Pinned : ScrollBehavior()
    object EnterAlways : ScrollBehavior()
    class ExitUntilCollapsed(
        val decayAnimationSpec: DecayAnimationSpec<Float>
    ) : ScrollBehavior()
}