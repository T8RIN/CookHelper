package ru.tech.cookhelper.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.harmonizeWithPrimary

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
    val md_theme_light_surfaceTint: Color

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
    val md_theme_dark_surfaceTint: Color

    object DarkBlue : ColorScheme {
        override val md_theme_light_primary = Color(0xFF4755B6)
        override val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        override val md_theme_light_primaryContainer = Color(0xFFDFE0FF)
        override val md_theme_light_onPrimaryContainer = Color(0xFF000D60)
        override val md_theme_light_secondary = Color(0xFF5B5D72)
        override val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        override val md_theme_light_secondaryContainer = Color(0xFFE0E1F9)
        override val md_theme_light_onSecondaryContainer = Color(0xFF181A2C)
        override val md_theme_light_tertiary = Color(0xFF77536C)
        override val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        override val md_theme_light_tertiaryContainer = Color(0xFFFFD7F0)
        override val md_theme_light_onTertiaryContainer = Color(0xFF2D1127)
        override val md_theme_light_error = Color(0xFFBA1A1A)
        override val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410002)
        override val md_theme_light_background = Color(0xFFFFFBFF)
        override val md_theme_light_onBackground = Color(0xFF1B1B1F)
        override val md_theme_light_surface = Color(0xFFFFFBFF)
        override val md_theme_light_onSurface = Color(0xFF1B1B1F)
        override val md_theme_light_surfaceVariant = Color(0xFFE3E1EC)
        override val md_theme_light_onSurfaceVariant = Color(0xFF46464F)
        override val md_theme_light_outline = Color(0xFF777680)
        override val md_theme_light_inverseOnSurface = Color(0xFFF3F0F4)
        override val md_theme_light_inverseSurface = Color(0xFF303034)
        override val md_theme_light_inversePrimary = Color(0xFFBCC3FF)

        override val md_theme_dark_primary = Color(0xFFBCC3FF)
        override val md_theme_dark_onPrimary = Color(0xFF112286)
        override val md_theme_dark_primaryContainer = Color(0xFF2E3C9D)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFDFE0FF)
        override val md_theme_dark_secondary = Color(0xFFC4C5DD)
        override val md_theme_dark_onSecondary = Color(0xFF2D2F42)
        override val md_theme_dark_secondaryContainer = Color(0xFF434559)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFE0E1F9)
        override val md_theme_dark_tertiary = Color(0xFFE6BAD7)
        override val md_theme_dark_onTertiary = Color(0xFF45263D)
        override val md_theme_dark_tertiaryContainer = Color(0xFF5D3C54)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFFFD7F0)
        override val md_theme_dark_error = Color(0xFFFFB4AB)
        override val md_theme_dark_errorContainer = Color(0xFF93000A)
        override val md_theme_dark_onError = Color(0xFF690005)
        override val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        override val md_theme_dark_background = Color(0xFF1B1B1F)
        override val md_theme_dark_onBackground = Color(0xFFE4E1E6)
        override val md_theme_dark_surface = Color(0xFF1B1B1F)
        override val md_theme_dark_onSurface = Color(0xFFE4E1E6)
        override val md_theme_dark_surfaceVariant = Color(0xFF46464F)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFC7C5D0)
        override val md_theme_dark_outline = Color(0xFF90909A)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1B1B1F)
        override val md_theme_dark_inverseSurface = Color(0xFFE4E1E6)
        override val md_theme_dark_inversePrimary = Color(0xFF4755B6)

        override val md_theme_light_surfaceTint get() = LightBlue.md_theme_dark_inversePrimary
        override val md_theme_dark_surfaceTint get() = LightBlue.md_theme_light_inversePrimary
    }

    object Blue : ColorScheme {
        override val md_theme_light_primary = Color(0xFF00658B)
        override val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        override val md_theme_light_primaryContainer = Color(0xFFC4E7FF)
        override val md_theme_light_onPrimaryContainer = Color(0xFF001E2C)
        override val md_theme_light_secondary = Color(0xFF4E616D)
        override val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        override val md_theme_light_secondaryContainer = Color(0xFFD1E5F4)
        override val md_theme_light_onSecondaryContainer = Color(0xFF0A1E28)
        override val md_theme_light_tertiary = Color(0xFF615A7D)
        override val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        override val md_theme_light_tertiaryContainer = Color(0xFFE7DEFF)
        override val md_theme_light_onTertiaryContainer = Color(0xFF1D1736)
        override val md_theme_light_error = Color(0xFFBA1A1A)
        override val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410002)
        override val md_theme_light_background = Color(0xFFFBFCFF)
        override val md_theme_light_onBackground = Color(0xFF191C1E)
        override val md_theme_light_surface = Color(0xFFFBFCFF)
        override val md_theme_light_onSurface = Color(0xFF191C1E)
        override val md_theme_light_surfaceVariant = Color(0xFFDDE3EA)
        override val md_theme_light_onSurfaceVariant = Color(0xFF41484D)
        override val md_theme_light_outline = Color(0xFF71787E)
        override val md_theme_light_inverseOnSurface = Color(0xFFF0F1F3)
        override val md_theme_light_inverseSurface = Color(0xFF2E3133)
        override val md_theme_light_inversePrimary = Color(0xFF7DD0FF)

        override val md_theme_dark_primary = Color(0xFF7DD0FF)
        override val md_theme_dark_onPrimary = Color(0xFF00344A)
        override val md_theme_dark_primaryContainer = Color(0xFF004C69)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFC4E7FF)
        override val md_theme_dark_secondary = Color(0xFFB5C9D7)
        override val md_theme_dark_onSecondary = Color(0xFF20333E)
        override val md_theme_dark_secondaryContainer = Color(0xFF374955)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFD1E5F4)
        override val md_theme_dark_tertiary = Color(0xFFCAC1E9)
        override val md_theme_dark_onTertiary = Color(0xFF322C4C)
        override val md_theme_dark_tertiaryContainer = Color(0xFF494264)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFE7DEFF)
        override val md_theme_dark_error = Color(0xFFFFB4AB)
        override val md_theme_dark_errorContainer = Color(0xFF93000A)
        override val md_theme_dark_onError = Color(0xFF690005)
        override val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        override val md_theme_dark_background = Color(0xFF191C1E)
        override val md_theme_dark_onBackground = Color(0xFFE1E2E5)
        override val md_theme_dark_surface = Color(0xFF191C1E)
        override val md_theme_dark_onSurface = Color(0xFFE1E2E5)
        override val md_theme_dark_surfaceVariant = Color(0xFF41484D)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFC0C7CD)
        override val md_theme_dark_outline = Color(0xFF8B9297)
        override val md_theme_dark_inverseOnSurface = Color(0xFF191C1E)
        override val md_theme_dark_inverseSurface = Color(0xFFE1E2E5)
        override val md_theme_dark_inversePrimary = Color(0xFF00658B)

        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
    }

    object LightBlue : ColorScheme {
        override val md_theme_light_primary = Color(0xFF006A68)
        override val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        override val md_theme_light_primaryContainer = Color(0xFF6FF7F4)
        override val md_theme_light_onPrimaryContainer = Color(0xFF00201F)
        override val md_theme_light_secondary = Color(0xFF4A6362)
        override val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        override val md_theme_light_secondaryContainer = Color(0xFFCCE8E6)
        override val md_theme_light_onSecondaryContainer = Color(0xFF051F1F)
        override val md_theme_light_tertiary = Color(0xFF4A607C)
        override val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        override val md_theme_light_tertiaryContainer = Color(0xFFD2E4FF)
        override val md_theme_light_onTertiaryContainer = Color(0xFF031C35)
        override val md_theme_light_error = Color(0xFFBA1A1A)
        override val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410002)
        override val md_theme_light_background = Color(0xFFF4FCFC)
        override val md_theme_light_onBackground = Color(0xFF191C1C)
        override val md_theme_light_surface = Color(0xFFF4FCFC)
        override val md_theme_light_onSurface = Color(0xFF191C1C)
        override val md_theme_light_surfaceVariant = Color(0xFFDAE5E3)
        override val md_theme_light_onSurfaceVariant = Color(0xFF3F4948)
        override val md_theme_light_outline = Color(0xFF6F7978)
        override val md_theme_light_inverseOnSurface = Color(0xFFEFF1F0)
        override val md_theme_light_inverseSurface = Color(0xFF2D3131)
        override val md_theme_light_inversePrimary = Color(0xFF4DDAD8)

        override val md_theme_dark_primary = Color(0xFF4DDAD8)
        override val md_theme_dark_onPrimary = Color(0xFF003736)
        override val md_theme_dark_primaryContainer = Color(0xFF00504F)
        override val md_theme_dark_onPrimaryContainer = Color(0xFF6FF7F4)
        override val md_theme_dark_secondary = Color(0xFFB0CCCA)
        override val md_theme_dark_onSecondary = Color(0xFF1B3534)
        override val md_theme_dark_secondaryContainer = Color(0xFF324B4A)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFCCE8E6)
        override val md_theme_dark_tertiary = Color(0xFFB2C8E8)
        override val md_theme_dark_onTertiary = Color(0xFF1B324B)
        override val md_theme_dark_tertiaryContainer = Color(0xFF334863)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFD2E4FF)
        override val md_theme_dark_error = Color(0xFFFFB4AB)
        override val md_theme_dark_errorContainer = Color(0xFF93000A)
        override val md_theme_dark_onError = Color(0xFF690005)
        override val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        override val md_theme_dark_background = Color(0xFF2A3030)
        override val md_theme_dark_onBackground = Color(0xFFE0E3E2)
        override val md_theme_dark_surface = Color(0xFF2A3030)
        override val md_theme_dark_onSurface = Color(0xFFE0E3E2)
        override val md_theme_dark_surfaceVariant = Color(0xFF3F4948)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFBEC9C8)
        override val md_theme_dark_outline = Color(0xFF889392)
        override val md_theme_dark_inverseOnSurface = Color(0xFF191C1C)
        override val md_theme_dark_inverseSurface = Color(0xFFE0E3E2)
        override val md_theme_dark_inversePrimary = Color(0xFF006A68)

        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
    }

    object Carrot : ColorScheme {
        override val md_theme_light_primary = Color(0xFF6E5D00)
        override val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        override val md_theme_light_primaryContainer = Color(0xFFFFE25F)
        override val md_theme_light_onPrimaryContainer = Color(0xFF221B00)
        override val md_theme_light_secondary = Color(0xFF665E40)
        override val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        override val md_theme_light_secondaryContainer = Color(0xFFEEE2BC)
        override val md_theme_light_onSecondaryContainer = Color(0xFF211B04)
        override val md_theme_light_tertiary = Color(0xFF43664E)
        override val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        override val md_theme_light_tertiaryContainer = Color(0xFFC5ECCE)
        override val md_theme_light_onTertiaryContainer = Color(0xFF00210F)
        override val md_theme_light_error = Color(0xFFBA1A1A)
        override val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410002)
        override val md_theme_light_background = Color(0xFFFFFBFF)
        override val md_theme_light_onBackground = Color(0xFF1D1B16)
        override val md_theme_light_surface = Color(0xFFFFFBFF)
        override val md_theme_light_onSurface = Color(0xFF1D1B16)
        override val md_theme_light_surfaceVariant = Color(0xFFEAE2D0)
        override val md_theme_light_onSurfaceVariant = Color(0xFF4B4739)
        override val md_theme_light_outline = Color(0xFF7C7767)
        override val md_theme_light_inverseOnSurface = Color(0xFFF6F0E7)
        override val md_theme_light_inverseSurface = Color(0xFF32302A)
        override val md_theme_light_inversePrimary = Color(0xFFE2C53D)

        override val md_theme_dark_primary = Color(0xFFE2C53D)
        override val md_theme_dark_onPrimary = Color(0xFF3A3000)
        override val md_theme_dark_primaryContainer = Color(0xFF534600)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFFFE25F)
        override val md_theme_dark_secondary = Color(0xFFD1C6A1)
        override val md_theme_dark_onSecondary = Color(0xFF363016)
        override val md_theme_dark_secondaryContainer = Color(0xFF4E472A)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFEEE2BC)
        override val md_theme_dark_tertiary = Color(0xFFA9D0B3)
        override val md_theme_dark_onTertiary = Color(0xFF153723)
        override val md_theme_dark_tertiaryContainer = Color(0xFF2C4E38)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFC5ECCE)
        override val md_theme_dark_error = Color(0xFFFFB4AB)
        override val md_theme_dark_errorContainer = Color(0xFF93000A)
        override val md_theme_dark_onError = Color(0xFF690005)
        override val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        override val md_theme_dark_background = Color(0xFF1D1B16)
        override val md_theme_dark_onBackground = Color(0xFFE7E2D9)
        override val md_theme_dark_surface = Color(0xFF1D1B16)
        override val md_theme_dark_onSurface = Color(0xFFE7E2D9)
        override val md_theme_dark_surfaceVariant = Color(0xFF4B4739)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFCDC6B4)
        override val md_theme_dark_outline = Color(0xFF969080)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1D1B16)
        override val md_theme_dark_inverseSurface = Color(0xFFE7E2D9)
        override val md_theme_dark_inversePrimary = Color(0xFF6E5D00)

        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
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
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
    }

    object Violet : ColorScheme {
        override val md_theme_light_primary = Color(0xFF7149AE)
        override val md_theme_light_onPrimary = Color(0xFFFFFFFF)
        override val md_theme_light_primaryContainer = Color(0xFFECDCFF)
        override val md_theme_light_onPrimaryContainer = Color(0xFF280056)
        override val md_theme_light_secondary = Color(0xFF645A70)
        override val md_theme_light_onSecondary = Color(0xFFFFFFFF)
        override val md_theme_light_secondaryContainer = Color(0xFFEBDEF7)
        override val md_theme_light_onSecondaryContainer = Color(0xFF1F182A)
        override val md_theme_light_tertiary = Color(0xFF7F525C)
        override val md_theme_light_onTertiary = Color(0xFFFFFFFF)
        override val md_theme_light_tertiaryContainer = Color(0xFFFFD9DF)
        override val md_theme_light_onTertiaryContainer = Color(0xFF32101A)
        override val md_theme_light_error = Color(0xFFBA1A1A)
        override val md_theme_light_errorContainer = Color(0xFFFFDAD6)
        override val md_theme_light_onError = Color(0xFFFFFFFF)
        override val md_theme_light_onErrorContainer = Color(0xFF410002)
        override val md_theme_light_background = Color(0xFFFFFBFF)
        override val md_theme_light_onBackground = Color(0xFF1D1B1E)
        override val md_theme_light_surface = Color(0xFFFFFBFF)
        override val md_theme_light_onSurface = Color(0xFF1D1B1E)
        override val md_theme_light_surfaceVariant = Color(0xFFE8E0EB)
        override val md_theme_light_onSurfaceVariant = Color(0xFF4A454E)
        override val md_theme_light_outline = Color(0xFF7B757F)
        override val md_theme_light_inverseOnSurface = Color(0xFFF5EFF4)
        override val md_theme_light_inverseSurface = Color(0xFF323033)
        override val md_theme_light_inversePrimary = Color(0xFFD6BAFF)

        override val md_theme_dark_primary = Color(0xFFD6BAFF)
        override val md_theme_dark_onPrimary = Color(0xFF41127C)
        override val md_theme_dark_primaryContainer = Color(0xFF582F94)
        override val md_theme_dark_onPrimaryContainer = Color(0xFFECDCFF)
        override val md_theme_dark_secondary = Color(0xFFCEC2DB)
        override val md_theme_dark_onSecondary = Color(0xFF352D40)
        override val md_theme_dark_secondaryContainer = Color(0xFF4C4357)
        override val md_theme_dark_onSecondaryContainer = Color(0xFFEBDEF7)
        override val md_theme_dark_tertiary = Color(0xFFF1B7C3)
        override val md_theme_dark_onTertiary = Color(0xFF4B252E)
        override val md_theme_dark_tertiaryContainer = Color(0xFF643B44)
        override val md_theme_dark_onTertiaryContainer = Color(0xFFFFD9DF)
        override val md_theme_dark_error = Color(0xFFFFB4AB)
        override val md_theme_dark_errorContainer = Color(0xFF93000A)
        override val md_theme_dark_onError = Color(0xFF690005)
        override val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
        override val md_theme_dark_background = Color(0xFF1D1B1E)
        override val md_theme_dark_onBackground = Color(0xFFE7E1E6)
        override val md_theme_dark_surface = Color(0xFF1D1B1E)
        override val md_theme_dark_onSurface = Color(0xFFE7E1E6)
        override val md_theme_dark_surfaceVariant = Color(0xFF4A454E)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFCBC4CF)
        override val md_theme_dark_outline = Color(0xFF958E99)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1D1B1E)
        override val md_theme_dark_inverseSurface = Color(0xFFE7E1E6)
        override val md_theme_dark_inversePrimary = Color(0xFF7149AE)

        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
    }

    object Purple : ColorScheme {
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
        override val md_theme_light_background = Color(0xFFFEF7FF)
        override val md_theme_light_onBackground = Color(0xFF1e1a1d)
        override val md_theme_light_surface = Color(0xFFFEF7FF)
        override val md_theme_light_onSurface = Color(0xFF1e1a1d)
        override val md_theme_light_surfaceVariant = Color(0xFFeddfe9)
        override val md_theme_light_onSurfaceVariant = Color(0xFF4d444c)
        override val md_theme_light_outline = Color(0xFF7f747d)
        override val md_theme_light_inverseOnSurface = Color(0xFFf7eef2)
        override val md_theme_light_inverseSurface = Color(0xFF342f33)
        override val md_theme_light_inversePrimary = Color(0xFFfea9ff)
        override val md_theme_light_surfaceTint get() = md_theme_dark_inversePrimary

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
        override val md_theme_dark_background = Color(0xFF36052A)
        override val md_theme_dark_onBackground = Color(0xFFe9e0e4)
        override val md_theme_dark_surface = Color(0xFF36052A)
        override val md_theme_dark_onSurface = Color(0xFFe9e0e4)
        override val md_theme_dark_surfaceVariant = Color(0xFF4d444c)
        override val md_theme_dark_onSurfaceVariant = Color(0xFFd0c3cc)
        override val md_theme_dark_outline = Color(0xFF998e96)
        override val md_theme_dark_inverseOnSurface = Color(0xFF1e1a1d)
        override val md_theme_dark_inverseSurface = Color(0xFFe9e0e4)
        override val md_theme_dark_inversePrimary = Color(0xFFa200bb)
        override val md_theme_dark_surfaceTint get() = md_theme_light_inversePrimary
    }

}

val colorList: List<ColorScheme> = listOf(
    ColorScheme.DarkBlue,
    ColorScheme.Blue,
    ColorScheme.LightBlue,
    ColorScheme.Mint,
    ColorScheme.Green,
    ColorScheme.Yellow,
    ColorScheme.Carrot,
    ColorScheme.Orange,
    ColorScheme.Red,
    ColorScheme.Pink,
    ColorScheme.Purple,
    ColorScheme.Violet,
)

val colorTitlesList: List<Int> = listOf(
    R.string.dark_blue,
    R.string.blue,
    R.string.light_blue,
    R.string.mint,
    R.string.green,
    R.string.yellow,
    R.string.carrot,
    R.string.orange,
    R.string.red,
    R.string.pink,
    R.string.purple,
    R.string.violet,
)

val ColorScheme.ordinal get() = colorList.indexOf(this)

val ColorScheme.title @Composable get() = stringResource(colorTitlesList[colorList.indexOf(this)])

inline val LikeColor: Color
    @Composable
    get() = Red

inline val Red: Color
    @Composable
    get() = Color(0xFFff4e3a).harmonizeWithPrimary()

inline val Orange: Color
    @Composable
    get() = Color(0xFFFB8C00).harmonizeWithPrimary()

inline val Green: Color
    @Composable
    get() = Color(0xFF56CA5B).harmonizeWithPrimary()

inline val Gray: Color
    @Composable
    get() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)