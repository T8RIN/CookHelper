package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.ui.graphics.Color

@Suppress("PropertyName")
sealed interface ColorScheme {
    val md_theme_light_primary: Color
    val md_theme_light_onPrimary: Color
    val md_theme_light_primaryContainer: Color
    val md_theme_light_onPrimaryContainer: Color
    val md_theme_light_secondary: Color
    val md_theme_light_onSecondary: Color
    val md_theme_light_secondaryContainer: Color
    val md_theme_light_onSecondaryContainer: Color
    val md_theme_light_tertiary: Color
    val md_theme_light_onTertiary: Color
    val md_theme_light_tertiaryContainer: Color
    val md_theme_light_onTertiaryContainer: Color
    val md_theme_light_error: Color
    val md_theme_light_errorContainer: Color
    val md_theme_light_onError: Color
    val md_theme_light_onErrorContainer: Color
    val md_theme_light_background: Color
    val md_theme_light_onBackground: Color
    val md_theme_light_surface: Color
    val md_theme_light_onSurface: Color
    val md_theme_light_surfaceVariant: Color
    val md_theme_light_onSurfaceVariant: Color
    val md_theme_light_outline: Color
    val md_theme_light_inverseOnSurface: Color
    val md_theme_light_inverseSurface: Color
    val md_theme_light_inversePrimary: Color

    val md_theme_dark_primary: Color
    val md_theme_dark_onPrimary: Color
    val md_theme_dark_primaryContainer: Color
    val md_theme_dark_onPrimaryContainer: Color
    val md_theme_dark_secondary: Color
    val md_theme_dark_onSecondary: Color
    val md_theme_dark_secondaryContainer: Color
    val md_theme_dark_onSecondaryContainer: Color
    val md_theme_dark_tertiary: Color
    val md_theme_dark_onTertiary: Color
    val md_theme_dark_tertiaryContainer: Color
    val md_theme_dark_onTertiaryContainer: Color
    val md_theme_dark_error: Color
    val md_theme_dark_errorContainer: Color
    val md_theme_dark_onError: Color
    val md_theme_dark_onErrorContainer: Color
    val md_theme_dark_background: Color
    val md_theme_dark_onBackground: Color
    val md_theme_dark_surface: Color
    val md_theme_dark_onSurface: Color
    val md_theme_dark_surfaceVariant: Color
    val md_theme_dark_onSurfaceVariant: Color
    val md_theme_dark_outline: Color
    val md_theme_dark_inverseOnSurface: Color
    val md_theme_dark_inverseSurface: Color
    val md_theme_dark_inversePrimary: Color

    object Violet : ColorScheme {
        override val md_theme_light_primary = Color(0xFFa200bb)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFffd5ff)
        override val md_theme_light_onPrimaryContainer = Color(0xFF35003f)
        override val md_theme_light_secondary = Color(0xFF6c586b)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFf5dbf1)
        override val md_theme_light_onSecondaryContainer = Color(0xFF251626)
        override val md_theme_light_tertiary = Color(0xFF825249)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFffdad2)
        override val md_theme_light_onTertiaryContainer = Color(0xFF33110b)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfcfcfc)
        override val md_theme_light_onBackground = Color(0xFF1e1a1d)
        override val md_theme_light_surface = Color(0xFFfcfcfc)
        override val md_theme_light_onSurface = Color(0xFF1e1a1d)
        override val md_theme_light_surfaceVariant = Color(0xFFeddfe9)
        override val md_theme_light_onSurfaceVariant = Color(0xFF4d444c)
        override val md_theme_light_outline = Color(0xFF7f747d)
        override val md_theme_light_inverseOnSurface = Color(0xFFf7eef2)
        override val md_theme_light_inverseSurface = Color(0xFF342f33)
        override val md_theme_light_inversePrimary = Color(0xFFfea9ff)

        override val md_theme_dark_primary = Color(0xFFfea9ff)
        override val md_theme_dark_onPrimary = Color(0xFF580066)
        override val md_theme_dark_primaryContainer = Color(0xFF7c008f)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFffd5ff)
        override val md_theme_dark_secondary = Color(0xFFd8bfd5)
        override val md_theme_dark_onSecondary = Color(0xFF3b2b3b)
        override val md_theme_dark_secondaryContainer = Color(0xFF534152)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFf5dbf1)
        override val md_theme_dark_tertiary = Color(0xFFf6b8ad)
        override val md_theme_dark_onTertiary = Color(0xFF4c251e)
        override val md_theme_dark_tertiaryContainer = Color(0xFF673b33)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFffdad2)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF1e1a1d)
        override val md_theme_dark_onBackground = Color(0xFFe9e0e4)
        override val md_theme_dark_surface = Color(0xFF1e1a1d)
        override val md_theme_dark_onSurface = Color(0xFFe9e0e4)
        override val md_theme_dark_surfaceVariant = Color(0xFF4d444c)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFd0c3cc)
        override val md_theme_dark_outline = Color(0xFF998e96)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1e1a1d)
        override val md_theme_dark_inverseSurface = Color(0xFFe9e0e4)
        override val md_theme_dark_inversePrimary = Color(0xFFa200bb)
    }

    object Blue : ColorScheme {
        override val md_theme_light_primary = Color(0xFF00658a)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFc2e8ff)
        override val md_theme_light_onPrimaryContainer = Color(0xFF001e2c)
        override val md_theme_light_secondary = Color(0xFF00687a)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFa6eeff)
        override val md_theme_light_onSecondaryContainer = Color(0xFF001f26)
        override val md_theme_light_tertiary = Color(0xFF6750a4)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFeaddff)
        override val md_theme_light_onTertiaryContainer = Color(0xFF22005d)
        override val md_theme_light_error = Color(0xFFB3261E)
        override val md_theme_light_errorContainer = Color(0xFFF9DEDC)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410E0B)
        override val md_theme_light_background = Color(0xFFFFFBFE)
        override val md_theme_light_onBackground = Color(0xFF1C1B1F)
        override val md_theme_light_surface = Color(0xFFFFFBFE)
        override val md_theme_light_onSurface = Color(0xFF1C1B1F)
        override val md_theme_light_surfaceVariant = Color(0xFFE7E0EC)
        override val md_theme_light_onSurfaceVariant = Color(0xFF49454F)
        override val md_theme_light_outline = Color(0xFF79747E)
        override val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
        override val md_theme_light_inverseSurface = Color(0xFF313033)
        override val md_theme_light_inversePrimary = Color(0xFF78d1ff)

        override val md_theme_dark_primary = Color(0xFF78d1ff)
        override val md_theme_dark_onPrimary = Color(0xFF003549)
        override val md_theme_dark_primaryContainer = Color(0xFF004c69)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFc2e8ff)
        override val md_theme_dark_secondary = Color(0xFF54d7f3)
        override val md_theme_dark_onSecondary = Color(0xFF003640)
        override val md_theme_dark_secondaryContainer = Color(0xFF004e5c)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFa6eeff)
        override val md_theme_dark_tertiary = Color(0xFFd0bcff)
        override val md_theme_dark_onTertiary = Color(0xFF381e73)
        override val md_theme_dark_tertiaryContainer = Color(0xFF4f378b)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFeaddff)
        override val md_theme_dark_error = Color(0xFFF2B8B5)
        override val md_theme_dark_errorContainer = Color(0xFF8C1D18)
        override val md_theme_dark_onError = Color(0xFF601410)
        override val md_theme_dark_onErrorContainer = Color(0xFFF9DEDC)
        override val md_theme_dark_background = Color(0xFF1C1B1F)
        override val md_theme_dark_onBackground = Color(0xFFE6E1E5)
        override val md_theme_dark_surface = Color(0xFF1C1B1F)
        override val md_theme_dark_onSurface = Color(0xFFE6E1E5)
        override val md_theme_dark_surfaceVariant = Color(0xFF49454F)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4D0)
        override val md_theme_dark_outline = Color(0xFF938F99)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1F)
        override val md_theme_dark_inverseSurface = Color(0xFFE6E1E5)
        override val md_theme_dark_inversePrimary = Color(0xFF00658a)

    }

    object Green : ColorScheme {
        override val md_theme_light_primary = Color(0xFF006e00)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFF76ff66)
        override val md_theme_light_onPrimaryContainer = Color(0xFF002200)
        override val md_theme_light_secondary = Color(0xFF53634e)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFd7e8cd)
        override val md_theme_light_onSecondaryContainer = Color(0xFF121f0f)
        override val md_theme_light_tertiary = Color(0xFF386568)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFbcebef)
        override val md_theme_light_onTertiaryContainer = Color(0xFF002022)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfcfcf6)
        override val md_theme_light_onBackground = Color(0xFF1a1c19)
        override val md_theme_light_surface = Color(0xFFfcfcf6)
        override val md_theme_light_onSurface = Color(0xFF1a1c19)
        override val md_theme_light_surfaceVariant = Color(0xFFdee4d7)
        override val md_theme_light_onSurfaceVariant = Color(0xFF43493f)
        override val md_theme_light_outline = Color(0xFF73796e)
        override val md_theme_light_inverseOnSurface = Color(0xFFf1f1eb)
        override val md_theme_light_inverseSurface = Color(0xFF2f312d)
        override val md_theme_light_inversePrimary = Color(0xFF24e529)

        override val md_theme_dark_primary = Color(0xFF24e529)
        override val md_theme_dark_onPrimary = Color(0xFF003a00)
        override val md_theme_dark_primaryContainer = Color(0xFF005300)
        override val md_theme_dark_onPrimaryContainer = Color(0xFF76ff66)
        override val md_theme_dark_secondary = Color(0xFFbbccb2)
        override val md_theme_dark_onSecondary = Color(0xFF263422)
        override val md_theme_dark_secondaryContainer = Color(0xFF3c4b37)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFd7e8cd)
        override val md_theme_dark_tertiary = Color(0xFFa0cfd2)
        override val md_theme_dark_onTertiary = Color(0xFF00373a)
        override val md_theme_dark_tertiaryContainer = Color(0xFF1e4d51)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFbcebef)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF1a1c19)
        override val md_theme_dark_onBackground = Color(0xFFe2e3dc)
        override val md_theme_dark_surface = Color(0xFF1a1c19)
        override val md_theme_dark_onSurface = Color(0xFFe2e3dc)
        override val md_theme_dark_surfaceVariant = Color(0xFF43493f)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFc2c8bc)
        override val md_theme_dark_outline = Color(0xFF8d9388)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1a1c19)
        override val md_theme_dark_inverseSurface = Color(0xFFe2e3dc)
        override val md_theme_dark_inversePrimary = Color(0xFF006e00)
    }

    object Mint : ColorScheme {
        override val md_theme_light_primary = Color(0xFF006d3c)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFF60fea6)
        override val md_theme_light_onPrimaryContainer = Color(0xFF00210e)
        override val md_theme_light_secondary = Color(0xFF4f6354)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFd2e8d5)
        override val md_theme_light_onSecondaryContainer = Color(0xFF0c1f13)
        override val md_theme_light_tertiary = Color(0xFF3a646f)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFbfeaf7)
        override val md_theme_light_onTertiaryContainer = Color(0xFF001f27)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfbfdf7)
        override val md_theme_light_onBackground = Color(0xFF191c1a)
        override val md_theme_light_surface = Color(0xFFfbfdf7)
        override val md_theme_light_onSurface = Color(0xFF191c1a)
        override val md_theme_light_surfaceVariant = Color(0xFFdce5db)
        override val md_theme_light_onSurfaceVariant = Color(0xFF414942)
        override val md_theme_light_outline = Color(0xFF717971)
        override val md_theme_light_inverseOnSurface = Color(0xFFf0f1ec)
        override val md_theme_light_inverseSurface = Color(0xFF2e312e)
        override val md_theme_light_inversePrimary = Color(0xFF3be18c)

        override val md_theme_dark_primary = Color(0xFF3be18c)
        override val md_theme_dark_onPrimary = Color(0xFF00391c)
        override val md_theme_dark_primaryContainer = Color(0xFF00522c)
        override val md_theme_dark_onPrimaryContainer = Color(0xFF60fea6)
        override val md_theme_dark_secondary = Color(0xFFb6ccb9)
        override val md_theme_dark_onSecondary = Color(0xFF213527)
        override val md_theme_dark_secondaryContainer = Color(0xFF384b3d)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFd2e8d5)
        override val md_theme_dark_tertiary = Color(0xFFa3cdda)
        override val md_theme_dark_onTertiary = Color(0xFF023640)
        override val md_theme_dark_tertiaryContainer = Color(0xFF214c57)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFbfeaf7)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF191c1a)
        override val md_theme_dark_onBackground = Color(0xFFe1e3de)
        override val md_theme_dark_surface = Color(0xFF191c1a)
        override val md_theme_dark_onSurface = Color(0xFFe1e3de)
        override val md_theme_dark_surfaceVariant = Color(0xFF414942)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFc1c9c0)
        override val md_theme_dark_outline = Color(0xFF8b938b)
        override val md_theme_dark_inverseOnSurface = Color(0xFF191c1a)
        override val md_theme_dark_inverseSurface = Color(0xFFe1e3de)
        override val md_theme_dark_inversePrimary = Color(0xFF006d3c)
    }

    object Yellow : ColorScheme {
        override val md_theme_light_primary = Color(0xFF606200)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFe7eb00)
        override val md_theme_light_onPrimaryContainer = Color(0xFF1c1d00)
        override val md_theme_light_secondary = Color(0xFF606043)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFe6e4c0)
        override val md_theme_light_onSecondaryContainer = Color(0xFF1c1d06)
        override val md_theme_light_tertiary = Color(0xFF3d6657)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFbfecd9)
        override val md_theme_light_onTertiaryContainer = Color(0xFF002117)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfffcf3)
        override val md_theme_light_onBackground = Color(0xFF1c1c16)
        override val md_theme_light_surface = Color(0xFFfffcf3)
        override val md_theme_light_onSurface = Color(0xFF1c1c16)
        override val md_theme_light_surfaceVariant = Color(0xFFe6e3d1)
        override val md_theme_light_onSurfaceVariant = Color(0xFF48473b)
        override val md_theme_light_outline = Color(0xFF797869)
        override val md_theme_light_inverseOnSurface = Color(0xFFf4f0e8)
        override val md_theme_light_inverseSurface = Color(0xFF31302b)
        override val md_theme_light_inversePrimary = Color(0xFFcace00)

        override val md_theme_dark_primary = Color(0xFFcace00)
        override val md_theme_dark_onPrimary = Color(0xFF313300)
        override val md_theme_dark_primaryContainer = Color(0xFF484a00)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFe7eb00)
        override val md_theme_dark_secondary = Color(0xFFc9c8a4)
        override val md_theme_dark_onSecondary = Color(0xFF323219)
        override val md_theme_dark_secondaryContainer = Color(0xFF48482d)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFe6e4c0)
        override val md_theme_dark_tertiary = Color(0xFFa3d0bd)
        override val md_theme_dark_onTertiary = Color(0xFF09372a)
        override val md_theme_dark_tertiaryContainer = Color(0xFF254e40)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFbfecd9)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF1c1c16)
        override val md_theme_dark_onBackground = Color(0xFFe5e2d9)
        override val md_theme_dark_surface = Color(0xFF1c1c16)
        override val md_theme_dark_onSurface = Color(0xFFe5e2d9)
        override val md_theme_dark_surfaceVariant = Color(0xFF48473b)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFc9c7b6)
        override val md_theme_dark_outline = Color(0xFF939182)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1c1c16)
        override val md_theme_dark_inverseSurface = Color(0xFFe5e2d9)
        override val md_theme_dark_inversePrimary = Color(0xFF606200)
    }

    object Orange : ColorScheme {
        override val md_theme_light_primary = Color(0xFF944b00)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFffdcc3)
        override val md_theme_light_onPrimaryContainer = Color(0xFF301400)
        override val md_theme_light_secondary = Color(0xFF755945)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFffdcc3)
        override val md_theme_light_onSecondaryContainer = Color(0xFF2a1708)
        override val md_theme_light_tertiary = Color(0xFF5e6135)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFe3e6af)
        override val md_theme_light_onTertiaryContainer = Color(0xFF1b1d00)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfcfcfc)
        override val md_theme_light_onBackground = Color(0xFF201a17)
        override val md_theme_light_surface = Color(0xFFfcfcfc)
        override val md_theme_light_onSurface = Color(0xFF201a17)
        override val md_theme_light_surfaceVariant = Color(0xFFf3ded2)
        override val md_theme_light_onSurfaceVariant = Color(0xFF52443b)
        override val md_theme_light_outline = Color(0xFF84746a)
        override val md_theme_light_inverseOnSurface = Color(0xFFfaeee7)
        override val md_theme_light_inverseSurface = Color(0xFF352f2b)
        override val md_theme_light_inversePrimary = Color(0xFFffb77f)

        override val md_theme_dark_primary = Color(0xFFffb77f)
        override val md_theme_dark_onPrimary = Color(0xFF502500)
        override val md_theme_dark_primaryContainer = Color(0xFF703700)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFffdcc3)
        override val md_theme_dark_secondary = Color(0xFFe4bfa7)
        override val md_theme_dark_onSecondary = Color(0xFF422b1a)
        override val md_theme_dark_secondaryContainer = Color(0xFF5b412f)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFffdcc3)
        override val md_theme_dark_tertiary = Color(0xFFc7ca95)
        override val md_theme_dark_onTertiary = Color(0xFF2f330b)
        override val md_theme_dark_tertiaryContainer = Color(0xFF464920)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFe3e6af)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF201a17)
        override val md_theme_dark_onBackground = Color(0xFFece0da)
        override val md_theme_dark_surface = Color(0xFF201a17)
        override val md_theme_dark_onSurface = Color(0xFFece0da)
        override val md_theme_dark_surfaceVariant = Color(0xFF52443b)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFd6c3b7)
        override val md_theme_dark_outline = Color(0xFF9e8d82)
        override val md_theme_dark_inverseOnSurface = Color(0xFF201a17)
        override val md_theme_dark_inverseSurface = Color(0xFFece0da)
        override val md_theme_dark_inversePrimary = Color(0xFF944b00)
    }

    object Red : ColorScheme {
        override val md_theme_light_primary = Color(0xFFc10000)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFffdad3)
        override val md_theme_light_onPrimaryContainer = Color(0xFF410000)
        override val md_theme_light_secondary = Color(0xFF775651)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFffdad3)
        override val md_theme_light_onSecondaryContainer = Color(0xFF2c1511)
        override val md_theme_light_tertiary = Color(0xFF705c2e)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFfbdfa5)
        override val md_theme_light_onTertiaryContainer = Color(0xFF261a00)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfcfcfc)
        override val md_theme_light_onBackground = Color(0xFF201a19)
        override val md_theme_light_surface = Color(0xFFfcfcfc)
        override val md_theme_light_onSurface = Color(0xFF201a19)
        override val md_theme_light_surfaceVariant = Color(0xFFf5deda)
        override val md_theme_light_onSurfaceVariant = Color(0xFF534341)
        override val md_theme_light_outline = Color(0xFF867370)
        override val md_theme_light_inverseOnSurface = Color(0xFFfbeeec)
        override val md_theme_light_inverseSurface = Color(0xFF362f2e)
        override val md_theme_light_inversePrimary = Color(0xFFffb4a6)

        override val md_theme_dark_primary = Color(0xFFffb4a6)
        override val md_theme_dark_onPrimary = Color(0xFF690000)
        override val md_theme_dark_primaryContainer = Color(0xFF940000)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFffdad3)
        override val md_theme_dark_secondary = Color(0xFFe7bdb6)
        override val md_theme_dark_onSecondary = Color(0xFF442925)
        override val md_theme_dark_secondaryContainer = Color(0xFF5d3f3a)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFffdad3)
        override val md_theme_dark_tertiary = Color(0xFFdec48c)
        override val md_theme_dark_onTertiary = Color(0xFF3e2e04)
        override val md_theme_dark_tertiaryContainer = Color(0xFF564418)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFfbdfa5)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF201a19)
        override val md_theme_dark_onBackground = Color(0xFFede0de)
        override val md_theme_dark_surface = Color(0xFF201a19)
        override val md_theme_dark_onSurface = Color(0xFFede0de)
        override val md_theme_dark_surfaceVariant = Color(0xFF534341)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFd8c2be)
        override val md_theme_dark_outline = Color(0xFFa08c89)
        override val md_theme_dark_inverseOnSurface = Color(0xFF201a19)
        override val md_theme_dark_inverseSurface = Color(0xFFede0de)
        override val md_theme_dark_inversePrimary = Color(0xFFc10000)
    }

    object Pink : ColorScheme {
        override val md_theme_light_primary = Color(0xFFb70071)
        override val md_theme_light_onPrimary = Color(0xFFffffff)
        override val md_theme_light_primaryContainer = Color(0xFFffd8e7)
        override val md_theme_light_onPrimaryContainer = Color(0xFF3d0022)
        override val md_theme_light_secondary = Color(0xFF735661)
        override val md_theme_light_onSecondary = Color(0xFFffffff)
        override val md_theme_light_secondaryContainer = Color(0xFFffd9e6)
        override val md_theme_light_onSecondaryContainer = Color(0xFF2a151e)
        override val md_theme_light_tertiary = Color(0xFF7e5538)
        override val md_theme_light_onTertiary = Color(0xFFffffff)
        override val md_theme_light_tertiaryContainer = Color(0xFFffdcc4)
        override val md_theme_light_onTertiaryContainer = Color(0xFF301400)
        override val md_theme_light_error = Color(0xFFba1b1b)
        override val md_theme_light_errorContainer = Color(0xFFffdad4)
        override val md_theme_light_onError = Color(0xFFffffff)
        override val md_theme_light_onErrorContainer = Color(0xFF410001)
        override val md_theme_light_background = Color(0xFFfcfcfc)
        override val md_theme_light_onBackground = Color(0xFF201a1c)
        override val md_theme_light_surface = Color(0xFFfcfcfc)
        override val md_theme_light_onSurface = Color(0xFF201a1c)
        override val md_theme_light_surfaceVariant = Color(0xFFf1dee3)
        override val md_theme_light_onSurfaceVariant = Color(0xFF514348)
        override val md_theme_light_outline = Color(0xFF837378)
        override val md_theme_light_inverseOnSurface = Color(0xFFf9eef0)
        override val md_theme_light_inverseSurface = Color(0xFF352f31)
        override val md_theme_light_inversePrimary = Color(0xFFffb0d2)

        override val md_theme_dark_primary = Color(0xFFffb0d2)
        override val md_theme_dark_onPrimary = Color(0xFF64003b)
        override val md_theme_dark_primaryContainer = Color(0xFF8c0055)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFffd8e7)
        override val md_theme_dark_secondary = Color(0xFFe1bdc9)
        override val md_theme_dark_onSecondary = Color(0xFF412a33)
        override val md_theme_dark_secondaryContainer = Color(0xFF593f49)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFffd9e6)
        override val md_theme_dark_tertiary = Color(0xFFf1bb97)
        override val md_theme_dark_onTertiary = Color(0xFF49290f)
        override val md_theme_dark_tertiaryContainer = Color(0xFF633e23)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFffdcc4)
        override val md_theme_dark_error = Color(0xFFffb4a9)
        override val md_theme_dark_errorContainer = Color(0xFF930006)
        override val md_theme_dark_onError = Color(0xFF680003)
        override val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
        override val md_theme_dark_background = Color(0xFF201a1c)
        override val md_theme_dark_onBackground = Color(0xFFebe0e2)
        override val md_theme_dark_surface = Color(0xFF201a1c)
        override val md_theme_dark_onSurface = Color(0xFFebe0e2)
        override val md_theme_dark_surfaceVariant = Color(0xFF514348)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFd4c2c7)
        override val md_theme_dark_outline = Color(0xFF9d8c91)
        override val md_theme_dark_inverseOnSurface = Color(0xFF201a1c)
        override val md_theme_dark_inverseSurface = Color(0xFFebe0e2)
        override val md_theme_dark_inversePrimary = Color(0xFFb70071)
    }

}

val colorList: List<ColorScheme> = listOf(
    ColorScheme.Blue,
    ColorScheme.Mint,
    ColorScheme.Green,
    ColorScheme.Yellow,
    ColorScheme.Orange,
    ColorScheme.Red,
    ColorScheme.Pink,
    ColorScheme.Violet
)

val LikeColor = Color(0xFFff4e3a)