package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.R
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
    val customAccent: Int = Color.Blue.toArgb(),
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

private inline operator fun <reified T> List<ModelSetting>.get(setting: Setting): T? {
    val option = getOrNull(setting.ordinal)?.option
    return when (T::class) {
        Boolean::class -> option?.toBooleanStrictOrNull()
        Int::class -> option?.toIntOrNull()
        Float::class -> option?.toFloatOrNull()
        String::class -> option
        else -> null
    } as? T
}

private fun Int?.toNightMode(): NightMode =
    enumValues<NightMode>()[this ?: NightMode.SYSTEM.ordinal]

fun List<ModelSetting>.mapToState(): SettingsState {
    val list = this.sortedBy { it.id }
    return SettingsState(
        dynamicColors = list[Setting.DYNAMIC_COLORS] ?: true,
        customAccent = list[Setting.CUSTOM_ACCENT] ?: Color.Blue.toArgb(),
        colorScheme = colorList.getOrNull(list.get<Int>(Setting.COLOR_SCHEME) ?: -1),
        nightMode = list.get<Int>(Setting.NIGHT_MODE).toNightMode(),
        cartConnection = list[Setting.CART_CONNECTION] ?: true,
        language = list[Setting.LANGUAGE] ?: "",
        fontScale = list[Setting.FONT_SCALE] ?: 1f,
        pureBlack = list[Setting.PURE_BLACK] ?: false,
        keepScreenOn = list[Setting.KEEP_SCREEN_ON] ?: false,
    )
}