package ru.tech.cookhelper.presentation.ui.utils.android

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

object ConfigurationUtils {

    val Configuration.isLandscape: Boolean
        get() {
            return orientation == Configuration.ORIENTATION_LANDSCAPE
        }

    val Configuration.isPortrait: Boolean
        get() {
            return orientation == Configuration.ORIENTATION_PORTRAIT
        }

    val Configuration.maxScreenDp: Dp
        get() {
            return max(screenHeightDp, screenWidthDp).dp
        }

    val Configuration.minScreenDp: Dp
        get() {
            return min(screenHeightDp, screenWidthDp).dp
        }
}