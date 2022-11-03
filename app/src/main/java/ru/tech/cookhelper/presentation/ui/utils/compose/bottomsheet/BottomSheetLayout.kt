package ru.tech.cookhelper.presentation.ui.utils.compose.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.android.Logger.makeLog
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Possible values of [BottomSheetState].
 */
enum class BottomSheetValue {
    /**
     * The bottom sheet is visible, but only showing its peek height.
     */
    Collapsed,

    /**
     * The bottom sheet is visible at its maximum height.
     */
    Expanded
}

/**
 * State of the persistent bottom sheet in [BottomSheetLayout].
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Stable
class BottomSheetState(
    initialValue: BottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
) : SwipeableState<BottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    /**
     * Whether the bottom sheet is expanded.
     */
    val isExpanded: Boolean
        get() = currentValue == BottomSheetValue.Expanded

    /**
     * Whether the bottom sheet is collapsed.
     */
    val isCollapsed: Boolean
        get() = currentValue == BottomSheetValue.Collapsed

    /**
     * Expand the bottom sheet with animation and suspend until it if fully expanded or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the expand animation ended
     */
    suspend fun expand() = animateTo(BottomSheetValue.Expanded)

    /**
     * Collapse the bottom sheet with animation and suspend until it if fully collapsed or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the collapse animation ended
     */
    suspend fun collapse() = animateTo(BottomSheetValue.Collapsed)

    override fun toString(): String {
        return "BottomSheetState(isExpanded=$isExpanded, isCollapsed=$isCollapsed, currentValue=$currentValue, targetValue=$targetValue)"
    }

    companion object {
        /**
         * The default [Saver] implementation for [BottomSheetState].
         */
        fun Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (BottomSheetValue) -> Boolean
        ): Saver<BottomSheetState, *> = Saver(
            save = { it.currentValue },
            restore = {
                BottomSheetState(
                    initialValue = it,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange
                )
            }
        )
    }

    internal val nestedScrollConnection = this.PreUpPostDownNestedScrollConnection
}

/**
 * Create a [BottomSheetState] and [remember] it.
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberBottomSheetState(
    initialValue: BottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
): BottomSheetState {
    return rememberSaveable(
        animationSpec,
        saver = BottomSheetState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        BottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    state: BottomSheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit,
    gesturesEnabled: Boolean = true,
    shape: Shape = BottomSheetDefaults.ContainerShape,
    elevation: Dp = 1.dp,
    sheetContainerColor: Color = MaterialTheme.colorScheme.surface,
    sheetContentColor: Color = contentColorFor(sheetContainerColor),
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    BoxWithConstraints(modifier) {
        val fullWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val fullHeight = constraints.maxHeight.toFloat()
        var bottomSheetHeight by remember { mutableStateOf(fullHeight) }

        val swipeable = Modifier
            .nestedScroll(state.nestedScrollConnection)
            .swipeable(
                state = state,
                anchors = mapOf(
                    fullHeight to BottomSheetValue.Collapsed,
                    fullHeight - bottomSheetHeight to BottomSheetValue.Expanded
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                velocityThreshold = 400.dp,
                orientation = Orientation.Vertical,
                enabled = gesturesEnabled,
                resistance = null
            )

        val sheetHorizontalPadding: Modifier
        val sheetWidth: Dp
        if (fullWidth > 640.dp) {
            sheetHorizontalPadding = Modifier.padding(horizontal = 56.dp)
            sheetWidth = 640.dp
        } else {
            sheetHorizontalPadding = Modifier
            sheetWidth = fullWidth
        }

        BottomSheetStack(
            body = {
                Box {
                    val sheetOpen by remember(state.progress) {
                        derivedStateOf {
                            state.progress.run { fraction != 1f || (from == to && from == BottomSheetValue.Expanded) }
                        }
                    }
                    val fraction by remember(state.progress) {
                        derivedStateOf {
                            state.progress.run {
                                val newFraction = fraction.takeIf { it != 1f } ?: 0f
                                if (from != to && from == BottomSheetValue.Expanded) {
                                    min(0.5f, (1 - newFraction) / 2)
                                } else if (from != to && from == BottomSheetValue.Collapsed) {
                                    min(0.5f, newFraction / 2)
                                } else if (from == BottomSheetValue.Expanded) {
                                    0.5f
                                } else {
                                    0f
                                }
                            }
                        }
                    }
                    content()
                    Scrim(
                        open = sheetOpen,
                        fraction = { fraction },
                        onDismiss = {
                            scope.launch { state.collapse() }
                        }
                    )
                }
            },
            bottomSheet = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        swipeable
                            .width(sheetWidth)
                            .then(sheetHorizontalPadding)
                            .onGloballyPositioned {
                                bottomSheetHeight = it.size.height.toFloat()
                            }
                            .padding(top = 72.dp),
                        shape = shape,
                        shadowElevation = elevation,
                        tonalElevation = elevation,
                        color = sheetContainerColor,
                        contentColor = sheetContentColor,
                        content = {
                            Column {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 22.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Spacer(Modifier.weight(1f))
                                    Surface(
                                        Modifier
                                            .width(32.dp)
                                            .height(4.dp),
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ) {}
                                    Spacer(Modifier.weight(1f))
                                }
                                sheetContent()
                            }
                        }
                    )
                }
            },
            bottomSheetOffset = state.offset,
        )
        LaunchedEffect(state.isCollapsed) {
            if (state.isCollapsed) onDismiss()
        }
    }
}

@Composable
private fun BottomSheetStack(
    body: @Composable () -> Unit,
    bottomSheet: @Composable () -> Unit,
    bottomSheetOffset: State<Float>,
) {
    Layout(
        content = {
            body()
            bottomSheet()
        }
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)

            val (sheetPlaceable) =
                measurables.drop(1).map {
                    it.measure(constraints.copy(minWidth = 0, minHeight = 0))
                }

            val sheetOffsetY = bottomSheetOffset.value.roundToInt().also { makeLog(it) }

            sheetPlaceable.placeRelative(0, sheetOffsetY)
        }
    }
}

@Composable
private fun Scrim(
    open: Boolean,
    onDismiss: () -> Unit,
    fraction: () -> Float
) {
    val color = MaterialTheme.colorScheme.scrim
    val dismissSheet = if (open) {
        Modifier.pointerInput(onDismiss) { detectTapGestures { onDismiss() } }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissSheet)
    ) {
        drawRect(color, alpha = fraction())
    }
}

object BottomSheetDefaults {

    val ContainerShape = RoundedCornerShape(
        topStart = 28.dp,
        topEnd = 28.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

}