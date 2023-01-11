package ru.tech.cookhelper.presentation.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.BuildConfig
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Marquee
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.settings.components.*
import ru.tech.cookhelper.presentation.settings.components.Setting.*
import ru.tech.cookhelper.presentation.settings.viewModel.SettingsViewModel
import ru.tech.cookhelper.presentation.ui.theme.*
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getCurrentLocaleString
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getLanguages
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.*
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    settingsState: SettingsState
) {
    val toastHost = LocalToastHost.current
    val context = LocalContext.current
    val dialogController = LocalDialogController.current

    LocalTopAppBarActions.current.setActions {
        IconButton(
            onClick = { dialogController.show(Dialog.AboutApp) },
            content = { Icon(Icons.Outlined.HelpOutline, null) }
        )
    }

    LazyColumn(
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        item {
            Column(Modifier.animateContentSize()) {
                var expandedNightMode by rememberSaveable { mutableStateOf(false) }
                PreferenceRow(
                    title = stringResource(NIGHT_MODE.title),
                    subtitle = NIGHT_MODE.subtitle?.let { stringResource(it) },
                    icon = NIGHT_MODE.getIcon(settingsState.nightMode),
                    onClick = { expandedNightMode = !expandedNightMode }
                ) {
                    val rotation: Float by animateFloatAsState(if (expandedNightMode) 180f else 0f)
                    IconButton(
                        onClick = { expandedNightMode = !expandedNightMode },
                        modifier = Modifier.rotate(rotation)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                if (expandedNightMode) {
                    Marquee {
                        ToggleGroupButton(
                            items = listOf(R.string.dark, R.string.light, R.string.system),
                            selectedIndex = settingsState.nightMode.ordinal,
                            indexChanged = {
                                viewModel.insertSetting(
                                    id = NIGHT_MODE.ordinal,
                                    option = it
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Separator()
            }
        }
        item {
            Column(Modifier.animateContentSize()) {
                var expanded by rememberSaveable { mutableStateOf(false) }
                val alpha by animateFloatAsState(targetValue = if (settingsState.dynamicColors) 0.5f else 1f)

                PreferenceRow(
                    modifier = Modifier.alpha(alpha),
                    title = stringResource(COLOR_SCHEME.title),
                    subtitle = COLOR_SCHEME.subtitle?.let { stringResource(it) },
                    icon = COLOR_SCHEME.getIcon(settingsState.nightMode),
                    onClick = {
                        if (!settingsState.dynamicColors) {
                            expanded = !expanded
                        } else toastHost.show(
                            Icons.Outlined.InvertColors,
                            (R.string.cannot_change_theme).asString(context)
                        )
                    }
                ) {
                    val rotation: Float by animateFloatAsState(if (expanded && !settingsState.dynamicColors) 180f else 0f)
                    IconButton(
                        onClick = {
                            if (!settingsState.dynamicColors) {
                                expanded = !expanded
                            } else toastHost.show(
                                Icons.Outlined.InvertColors,
                                (R.string.cannot_change_theme).asString(context)
                            )
                        },
                        modifier = Modifier.rotate(rotation),
                        enabled = !settingsState.dynamicColors
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                if (expanded && !settingsState.dynamicColors) {
                    Spacer(Modifier.height(10.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(colorList) { scheme ->
                            AppThemeItem(
                                selected = scheme.ordinal == settingsState.colorScheme.ordinal,
                                colorScheme = scheme.toColorScheme(),
                                onClick = {
                                    viewModel.insertSetting(
                                        id = COLOR_SCHEME.ordinal,
                                        option = scheme.ordinal
                                    )
                                }
                            )
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
                Separator()
            }
        }
        item {
            Column(Modifier.animateContentSize()) {
                var expanded by rememberSaveable { mutableStateOf(false) }
                PreferenceRow(
                    title = stringResource(R.string.theme_preview),
                    icon = Icons.Outlined.Preview,
                    onClick = { expanded = !expanded }
                ) {
                    val rotation: Float by animateFloatAsState(if (expanded) 180f else 0f)
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.rotate(rotation)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                if (expanded) {
                    ColorSchemePreview()
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Separator()
            }
        }
        item {
            PreferenceRowSwitch(
                title = stringResource(DYNAMIC_COLORS.title),
                subtitle = DYNAMIC_COLORS.subtitle?.let { stringResource(it) },
                icon = DYNAMIC_COLORS.getIcon(settingsState.nightMode),
                checked = settingsState.dynamicColors,
                onClick = {
                    viewModel.insertSetting(
                        id = DYNAMIC_COLORS.ordinal,
                        option = !settingsState.dynamicColors
                    )
                }
            )
            Separator()
        }
        item {
            Column(Modifier.animateContentSize()) {
                PreferenceRow(
                    title = stringResource(LANGUAGE.title),
                    icon = LANGUAGE.getIcon(settingsState.nightMode),
                    action = {
                        Text(
                            text = remember { context.getCurrentLocaleString() },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                            )
                        )
                    },
                    onClick = {
                        dialogController.show(
                            Dialog.PickLanguageDialog(
                                entries = context.getLanguages(),
                                selected = context.getCurrentLocaleString(),
                                onSelect = {
                                    viewModel.insertSetting(LANGUAGE.ordinal, it)
                                    val locale = if (it == "") {
                                        LocaleListCompat.getEmptyLocaleList()
                                    } else {
                                        LocaleListCompat.forLanguageTags(it)
                                    }
                                    AppCompatDelegate.setApplicationLocales(locale)
                                },
                                onDismiss = { dialogController.close() }
                            )
                        )
                    }
                )
                Separator()
            }
        }
        item {
            PreferenceRowSwitch(
                title = stringResource(CART_CONNECTION.title),
                subtitle = CART_CONNECTION.subtitle?.let { stringResource(it) },
                icon = CART_CONNECTION.getIcon(settingsState.nightMode),
                checked = settingsState.cartConnection,
                onClick = {
                    viewModel.insertSetting(
                        id = CART_CONNECTION.ordinal,
                        option = !settingsState.cartConnection
                    )
                }
            )
            Separator()
        }
        item {
            PreferenceRow(
                title = stringResource(R.string.app_name),
                action = {
                    Text(
                        text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                        )
                    )
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .size(60.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(SquircleShape(20.dp))
                                .combinedClickable(
                                    onLongClick = {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://github.com/T8RIN/CookHelper")
                                            )
                                        )
                                    },
                                    onClick = {
                                        toastHost.show(
                                            Icons.Outlined.GetApp,
                                            (R.string.long_click_to_go).asString(context)
                                        )
                                    }
                                )
                                .align(Alignment.Center),
                            shape = SquircleShape(20.dp),
                            color = MaterialTheme.colorScheme.background.createSecondaryColor(0.05f),
                            content = {}
                        )
                        Picture(
                            model = R.drawable.ic_launcher_foreground,
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.Center),
                            shimmerEnabled = false
                        )
                    }
                },
                onLongClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/T8RIN/CookHelper")
                        )
                    )
                },
                onClick = {
                    toastHost.show(
                        Icons.Outlined.GetApp,
                        (R.string.long_click_to_go).asString(context)
                    )
                }
            )
        }
    }

}

fun Setting.getIcon(nightMode: NightMode): ImageVector {
    return when (this) {
        NIGHT_MODE -> when (nightMode) {
            NightMode.DARK -> Icons.Outlined.ModeNight
            NightMode.LIGHT -> Icons.Outlined.WbSunny
            NightMode.SYSTEM -> Icons.Outlined.SettingsSystemDaydream
        }
        DYNAMIC_COLORS -> Icons.Outlined.InvertColors
        COLOR_SCHEME -> Icons.Outlined.Palette
        CART_CONNECTION -> Icons.Outlined.ShoppingCart
        LANGUAGE -> Icons.Outlined.Translate
    }
}

val Setting.title: Int
    get() {
        return when (this) {
            LANGUAGE -> R.string.language
            NIGHT_MODE -> R.string.app_theme_mode
            DYNAMIC_COLORS -> R.string.dynamic_сolors
            COLOR_SCHEME -> R.string.color_scheme
            CART_CONNECTION -> R.string.cart_connection
        }
    }

val Setting.subtitle: Int?
    get() {
        return when (this) {
            LANGUAGE, NIGHT_MODE, COLOR_SCHEME -> null
            DYNAMIC_COLORS -> R.string.dynamic_colors_subtitle
            CART_CONNECTION -> R.string.cart_connection_subtitle
        }
    }

@Composable
private fun AppThemeItem(
    colorScheme: ColorScheme,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(115.dp)
            .padding(start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppThemePreviewItem(
            selected = selected,
            onClick = onClick,
            colorScheme = colorScheme,
            shapes = MaterialTheme.shapes
        )
    }
}