package ru.tech.cookhelper.presentation.settings.components

import ru.tech.cookhelper.presentation.ui.theme.ColorScheme

data class SettingsState(
    val dynamicColors: Boolean = true,
    val colorScheme: ColorScheme = ColorScheme.Blue,
    val cartConnection: Boolean = true,
    val nightMode: NightMode = NightMode.SYSTEM
)

enum class NightMode {
    DARK, LIGHT, SYSTEM
}

enum class Settings {
    NIGHT_MODE, COLOR_SCHEME, DYNAMIC_COLORS, CART_CONNECTION
}