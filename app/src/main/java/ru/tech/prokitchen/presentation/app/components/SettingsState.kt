package ru.tech.prokitchen.presentation.app.components

data class SettingsState(
    val dynamicColors: Boolean = true,
    val colorScheme: ColorScheme = ColorScheme.BLUE,
    val cartConnection: Boolean = true,
    val nightMode: NightMode = NightMode.SYSTEM
)

enum class ColorScheme {
    BLUE, MINT, GREEN, YELLOW, ORANGE, RED, PINK, VIOLET
}

enum class NightMode {
    DARK, LIGHT, SYSTEM
}

enum class Settings {
    NIGHT_MODE, COLOR_SCHEME, DYNAMIC_COLORS, CART_CONNECTION
}