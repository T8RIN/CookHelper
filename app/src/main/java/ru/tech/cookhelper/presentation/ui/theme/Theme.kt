package ru.tech.cookhelper.presentation.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.FloatRange
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cookhelper.dynamic.theme.rememberColorScheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.tech.cookhelper.presentation.settings.components.NightMode
import ru.tech.cookhelper.presentation.settings.components.SettingsState
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createInverseSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.darken
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.lighten
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSettingsProvider
import ru.tech.cookhelper.presentation.ui.widgets.KeepScreenOn
import androidx.compose.material3.ColorScheme as Material3ColorScheme

private val ColorScheme.LightThemeColors: Material3ColorScheme
    get() = lightColorScheme(
        primary = md_theme_light_primary,
        onPrimary = md_theme_light_onPrimary,
        primaryContainer = md_theme_light_primaryContainer,
        onPrimaryContainer = md_theme_light_onPrimaryContainer,
        secondary = md_theme_light_secondary,
        onSecondary = md_theme_light_onSecondary,
        secondaryContainer = md_theme_light_secondaryContainer,
        onSecondaryContainer = md_theme_light_onSecondaryContainer,
        tertiary = md_theme_light_tertiary,
        onTertiary = md_theme_light_onTertiary,
        tertiaryContainer = md_theme_light_tertiaryContainer,
        onTertiaryContainer = md_theme_light_onTertiaryContainer,
        error = md_theme_light_error,
        errorContainer = md_theme_light_errorContainer,
        onError = md_theme_light_onError,
        onErrorContainer = md_theme_light_onErrorContainer,
        background = md_theme_light_background,
        onBackground = md_theme_light_onBackground,
        surface = md_theme_light_surface,
        onSurface = md_theme_light_onSurface,
        surfaceVariant = md_theme_light_surfaceVariant,
        onSurfaceVariant = md_theme_light_onSurfaceVariant,
        outline = md_theme_light_outline,
        inverseOnSurface = md_theme_light_inverseOnSurface,
        inverseSurface = md_theme_light_inverseSurface,
        inversePrimary = md_theme_light_inversePrimary,
        surfaceTint = md_theme_light_surfaceTint,
        outlineVariant = md_theme_light_surfaceVariant.darken(0.05f)
    )
private val ColorScheme.DarkThemeColors: Material3ColorScheme
    get() = darkColorScheme(
        primary = md_theme_dark_primary,
        onPrimary = md_theme_dark_onPrimary,
        primaryContainer = md_theme_dark_primaryContainer,
        onPrimaryContainer = md_theme_dark_onPrimaryContainer,
        secondary = md_theme_dark_secondary,
        onSecondary = md_theme_dark_onSecondary,
        secondaryContainer = md_theme_dark_secondaryContainer,
        onSecondaryContainer = md_theme_dark_onSecondaryContainer,
        tertiary = md_theme_dark_tertiary,
        onTertiary = md_theme_dark_onTertiary,
        tertiaryContainer = md_theme_dark_tertiaryContainer,
        onTertiaryContainer = md_theme_dark_onTertiaryContainer,
        error = md_theme_dark_error,
        errorContainer = md_theme_dark_errorContainer,
        onError = md_theme_dark_onError,
        onErrorContainer = md_theme_dark_onErrorContainer,
        background = md_theme_dark_background,
        onBackground = md_theme_dark_onBackground,
        surface = md_theme_dark_surface,
        onSurface = md_theme_dark_onSurface,
        surfaceVariant = md_theme_dark_surfaceVariant,
        onSurfaceVariant = md_theme_dark_onSurfaceVariant,
        outline = md_theme_dark_outline,
        inverseOnSurface = md_theme_dark_inverseOnSurface,
        inverseSurface = md_theme_dark_inverseSurface,
        inversePrimary = md_theme_dark_inversePrimary,
        surfaceTint = md_theme_dark_surfaceTint,
        outlineVariant = md_theme_dark_surfaceVariant.lighten(0.05f)
    )

@Composable
fun CookHelperTheme(
    dynamicColor: Boolean = LocalSettingsProvider.current.dynamicColors,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            LocalSettingsProvider.current.getColorScheme()
        }
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isDarkMode()
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = {
            val color = NavigationBarDefaults.containerColor.createInverseSecondaryColor()

            SideEffect {
                systemUiController.apply {
                    setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                    setNavigationBarColor(
                        color = if (landscape) color else Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }
            }
            content()
        }
    )

    KeepScreenOn(LocalSettingsProvider.current.keepScreenOn)
}

@Composable
fun isDarkMode() = when (LocalSettingsProvider.current.nightMode) {
    NightMode.SYSTEM -> isSystemInDarkTheme()
    NightMode.DARK -> true
    NightMode.LIGHT -> false
}

@Composable
fun SettingsState.getColorScheme(darkTheme: Boolean = isDarkMode()): Material3ColorScheme {
    return colorScheme.run {
        if (this == null) rememberColorScheme(isDarkTheme = darkTheme, color = customAccent)
        else if (darkTheme) DarkThemeColors
        else LightThemeColors
    }.run {
        if (pureBlack && isDarkMode()) copy(
            surface = Color.Black,
            background = Color.Black
        )
        else this
    }
}

@Composable
fun ColorScheme.toColorScheme(
    settingsState: SettingsState = LocalSettingsProvider.current
): Material3ColorScheme {
    val scheme = if (isDarkMode()) DarkThemeColors
    else LightThemeColors
    return scheme.run {
        if (settingsState.pureBlack && isDarkMode()) copy(
            surface = Color.Black,
            background = Color.Black
        )
        else this
    }
}

@Composable
fun Material3ColorScheme.scrimColor(
    @FloatRange(
        0.0,
        1.0
    ) alpha: Float = 0.75f
): Color = surfaceColorAtElevation(3.dp).copy(alpha = alpha)