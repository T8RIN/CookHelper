package ru.tech.cookhelper.presentation.ui.utils

import androidx.annotation.FloatRange
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ru.tech.cookhelper.presentation.app.components.NightMode
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSettingsProvider
import androidx.core.graphics.ColorUtils as AndroidColorUtils

object ColorUtils {

    fun Color.blend(
        color: Color,
        @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
    ): Color = AndroidColorUtils.blendARGB(this.toArgb(), color.toArgb(), fraction).toColor()

    fun Color.darken(
        @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
    ): Color = blend(color = Color.Black, fraction = fraction)

    @Composable
    fun Color.createSecondaryColor(
        @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
    ): Color {
        val darkTheme = when (LocalSettingsProvider.current.nightMode) {
            NightMode.SYSTEM -> isSystemInDarkTheme()
            NightMode.DARK -> true
            NightMode.LIGHT -> false
        }
        return if (darkTheme) lighten(fraction) else darken(fraction)
    }

    fun Color.lighten(
        @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
    ): Color = blend(color = Color.White, fraction = fraction)

    fun Int.toColor(): Color = Color(color = this)

}
