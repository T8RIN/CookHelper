package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.viewModel.toBoolean
import ru.tech.cookhelper.presentation.ui.theme.ColorScheme
import ru.tech.cookhelper.presentation.ui.theme.colorList
import ru.tech.cookhelper.domain.model.Setting as ModelSetting

data class SettingsState(
    val dynamicColors: Boolean = true,
    val colorScheme: ColorScheme? = ColorScheme.Blue,
    val cartConnection: Boolean = true,
    val nightMode: NightMode = NightMode.SYSTEM,
    val language: String = "",
    val fontScale: Float = 1f,
    val customAccent: Color = Color.Blue,
    val pureBlack: Boolean = false,
    val keepScreenOn: Boolean = true
)

enum class NightMode {
    DARK, LIGHT, SYSTEM
}

enum class Setting {
    NIGHT_MODE, COLOR_SCHEME, DYNAMIC_COLORS, CART_CONNECTION, LANGUAGE, FONT_SCALE, CUSTOM_ACCENT, PURE_BLACK, KEEP_SCREEN_ON
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
        Setting.PURE_BLACK -> Icons.Outlined.Compare
        Setting.KEEP_SCREEN_ON -> Icons.Outlined.Smartphone
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
            Setting.PURE_BLACK -> R.string.pure_black
            Setting.KEEP_SCREEN_ON -> R.string.keep_screen_on
        }
    }

val Setting.subtitle: Int?
    get() {
        return when (this) {
            Setting.DYNAMIC_COLORS -> R.string.dynamic_colors_subtitle
            Setting.CART_CONNECTION -> R.string.cart_connection_subtitle
            Setting.FONT_SCALE -> R.string.font_size_subtitle
            Setting.PURE_BLACK -> R.string.pure_black_subtitle
            Setting.KEEP_SCREEN_ON -> R.string.keep_screen_on_subtitle
            else -> null
        }
    }

fun List<ModelSetting>.mapToState(): SettingsState {
    var state = SettingsState()
    forEach { setting ->
        when (setting.id) {
            Setting.DYNAMIC_COLORS.ordinal -> {
                state = state.copy(dynamicColors = setting.option.toBoolean())
            }
            Setting.CUSTOM_ACCENT.ordinal -> {
                state = state.copy(
                    customAccent = setting.option.toLongOrNull()?.let { Color(it) } ?: Color.Blue
                )
            }
            Setting.COLOR_SCHEME.ordinal -> {
                val index = setting.option.toIntOrNull() ?: -1
                state = state.copy(colorScheme = colorList.getOrNull(index))
            }
            Setting.NIGHT_MODE.ordinal -> {
                val index = setting.option.toIntOrNull() ?: NightMode.SYSTEM.ordinal
                state = state.copy(nightMode = enumValues<NightMode>()[index])
            }
            Setting.CART_CONNECTION.ordinal -> {
                state = state.copy(
                    cartConnection = setting.option.toBoolean()
                )
            }
            Setting.LANGUAGE.ordinal -> {
                state = state.copy(
                    language = setting.option
                )
            }
            Setting.FONT_SCALE.ordinal -> {
                state = state.copy(
                    fontScale = setting.option.toFloatOrNull() ?: 1f
                )
            }
            Setting.PURE_BLACK.ordinal -> {
                state = state.copy(
                    pureBlack = setting.option.toBooleanStrictOrNull() ?: false
                )
            }
            Setting.KEEP_SCREEN_ON.ordinal -> {
                state = state.copy(
                    keepScreenOn = setting.option.toBooleanStrictOrNull() ?: false
                )
            }
        }
    }
    return state
}