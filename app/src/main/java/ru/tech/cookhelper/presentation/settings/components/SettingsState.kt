package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.ColorScheme

data class SettingsState(
    val dynamicColors: Boolean = true,
    val colorScheme: ColorScheme? = ColorScheme.Blue,
    val cartConnection: Boolean = true,
    val nightMode: NightMode = NightMode.SYSTEM,
    val language: String = "",
    val fontScale: Float = 1f,
    val customAccent: Color = Color.Blue
)

enum class NightMode {
    DARK, LIGHT, SYSTEM
}

enum class Setting {
    NIGHT_MODE, COLOR_SCHEME, DYNAMIC_COLORS, CART_CONNECTION, LANGUAGE, FONT_SCALE, CUSTOM_ACCENT
}

fun Setting.getIcon(nightMode: NightMode): ImageVector {
    return when (this) {
        Setting.NIGHT_MODE -> when (nightMode) {
            NightMode.DARK -> Icons.Outlined.ModeNight
            NightMode.LIGHT -> Icons.Outlined.WbSunny
            NightMode.SYSTEM -> Icons.Outlined.SettingsSystemDaydream
        }
        Setting.DYNAMIC_COLORS -> Icons.Outlined.InvertColors
        Setting.COLOR_SCHEME, Setting.CUSTOM_ACCENT -> Icons.Outlined.Palette
        Setting.CART_CONNECTION -> Icons.Outlined.ShoppingCart
        Setting.LANGUAGE -> Icons.Outlined.Translate
        Setting.FONT_SCALE -> Icons.Outlined.FontDownload
    }
}

val Setting.title: Int
    get() {
        return when (this) {
            Setting.LANGUAGE -> R.string.language
            Setting.NIGHT_MODE -> R.string.app_theme_mode
            Setting.DYNAMIC_COLORS -> R.string.dynamic_сolors
            Setting.COLOR_SCHEME, Setting.CUSTOM_ACCENT -> R.string.color_scheme
            Setting.CART_CONNECTION -> R.string.cart_connection
            Setting.FONT_SCALE -> R.string.font_size
        }
    }

val Setting.subtitle: Int?
    get() {
        return when (this) {
            Setting.DYNAMIC_COLORS -> R.string.dynamic_colors_subtitle
            Setting.CART_CONNECTION -> R.string.cart_connection_subtitle
            Setting.FONT_SCALE -> R.string.font_size_subtitle
            else -> null
        }
    }