package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable

sealed class ScrollBehavior {

    /**
     * Returns a pinned [TopAppBarScrollBehavior] that tracks nested-scroll callbacks and
     * updates its [TopAppBarState.contentOffset] accordingly.
     */
    object Pinned : ScrollBehavior()


    /**
     * Returns a [TopAppBarScrollBehavior]. A top app bar that is set up with this
     * [TopAppBarScrollBehavior] will immediately collapse when the content is pulled up, and will
     * immediately appear when the content is pulled down.
     *
     * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top app bar snaps
     * to either fully collapsed or fully extended state when a fling or a drag scrolled it into an
     * intermediate position
     * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
     * app bar when the user flings the app bar itself, or the content below it
     */
    class EnterAlways(
        val snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        val flingAnimationSpec: @Composable () -> DecayAnimationSpec<Float>? = { rememberSplineBasedDecay() }
    ) : ScrollBehavior()

    /**
     * Returns a [TopAppBarScrollBehavior] that adjusts its properties to affect the colors and
     * height of the top app bar.
     *
     * A top app bar that is set up with this [TopAppBarScrollBehavior] will immediately collapse
     * when the nested content is pulled up, and will expand back the collapsed area when the
     * content is  pulled all the way down.
     *
     * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top app bar snaps
     * to either fully collapsed or fully extended state when a fling or a drag scrolled it into an
     * intermediate position
     * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
     * app bar when the user flings the app bar itself, or the content below it
     */
    class ExitUntilCollapsed(
        val snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        val flingAnimationSpec: @Composable () -> DecayAnimationSpec<Float>? = { rememberSplineBasedDecay() }
    ) : ScrollBehavior()
}