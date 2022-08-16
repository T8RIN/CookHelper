package ru.tech.cookhelper.presentation.ui.utils.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

object PaddingUtils {
    @Composable
    fun PaddingValues.addPadding(
        bottom: Dp = 0.dp,
        top: Dp = 0.dp,
        start: Dp = 0.dp,
        end: Dp = 0.dp
    ): PaddingValues {
        return PaddingValues(
            start = abs(calculateStartPadding(LocalLayoutDirection.current) + start),
            top = abs(calculateTopPadding() + top),
            end = abs(calculateEndPadding(LocalLayoutDirection.current) + end),
            bottom = abs(calculateBottomPadding() + bottom)
        )
    }

    fun abs(dp: Dp): Dp = abs(dp.value).dp

    @Composable
    fun PaddingValues.removePadding(
        bottom: Dp = 0.dp,
        top: Dp = 0.dp,
        start: Dp = 0.dp,
        end: Dp = 0.dp
    ): PaddingValues {
        return addPadding(
            bottom = -bottom,
            top = -top,
            start = -start,
            end = -end
        )
    }
}