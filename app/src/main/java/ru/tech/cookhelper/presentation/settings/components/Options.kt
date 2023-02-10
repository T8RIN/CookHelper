@file:Suppress("unused")

package ru.tech.cookhelper.presentation.settings.components

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.GetApp
import androidx.compose.material.icons.outlined.InvertColors
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.cookhelper.dynamic.theme.rememberColorScheme
import ru.tech.cookhelper.BuildConfig
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.*
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getCurrentLocaleString
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.getLanguages
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.utils.provider.close
import ru.tech.cookhelper.presentation.ui.utils.provider.show
import ru.tech.cookhelper.presentation.ui.widgets.marquee.Marquee

@Composable
fun SettingsState.ThemeOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.NIGHT_MODE
    Column(Modifier.animateContentSize()) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        PreferenceRow(
            title = stringResource(option.title),
            subtitle = option.subtitle?.let { stringResource(it) },
            icon = option.getIcon(nightMode),
            onClick = { expanded = !expanded }
        ) {
            RotationButton(
                rotated = expanded,
                onClick = { expanded = !expanded }
            )
        }
        if (expanded) {
            ToggleGroupButton(
                items = listOf(R.string.dark, R.string.light, R.string.system),
                selectedIndex = nightMode.ordinal,
                indexChanged = {
                    insertSetting(option.ordinal, it)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Separator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsState.ColorSchemeOption(insertSetting: (id: Int, option: Any?) -> Unit) {
    val toastHost = LocalToastHostState.current
    val context = LocalContext.current
    var showColorPicker by rememberSaveable { mutableStateOf(false) }
    val option = Setting.COLOR_SCHEME

    Column(Modifier.animateContentSize()) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val alpha by animateFloatAsState(targetValue = if (dynamicColors) 0.5f else 1f)

        PreferenceRow(
            modifier = Modifier.alpha(alpha),
            title = stringResource(option.title),
            subtitle = option.subtitle?.let { stringResource(it) },
            icon = option.getIcon(nightMode),
            onClick = {
                if (!dynamicColors) {
                    expanded = !expanded
                } else toastHost.show(
                    Icons.Outlined.InvertColors,
                    (R.string.cannot_change_theme).asString(context)
                )
            }
        ) {
            RotationButton(
                rotated = expanded && !dynamicColors,
                onClick = {
                    if (!dynamicColors) {
                        expanded = !expanded
                    } else toastHost.show(
                        Icons.Outlined.InvertColors,
                        (R.string.cannot_change_theme).asString(context)
                    )
                }
            )
        }
        if (expanded && !dynamicColors) {
            val state = rememberLazyListState()
            Spacer(Modifier.height(10.dp))
            LazyRow(
                state = state,
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    AppThemeItem(
                        icon = Icons.Rounded.CreateAlt,
                        title = stringResource(R.string.custom),
                        selected = colorScheme == null,
                        colorScheme = rememberColorScheme(
                            isDarkTheme = isDarkMode(),
                            color = Color(customAccent)
                        ).run {
                            val darkMode = isDarkMode()
                            remember(this) {
                                if (pureBlack && darkMode) copy(
                                    surface = Color.Black,
                                    background = Color.Black
                                )
                                else this
                            }
                        },
                        onClick = {
                            if (colorScheme == null) showColorPicker = true
                            insertSetting(option.ordinal, null)
                        }
                    )
                    Spacer(Modifier.padding(4.dp))
                    Box(
                        Modifier
                            .width(1.dp)
                            .height(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                            .animateContentSize()
                    )
                    Spacer(Modifier.padding(4.dp))
                }
                items(colorList) { scheme ->
                    AppThemeItem(
                        title = scheme.title,
                        selected = scheme.ordinal == colorScheme?.ordinal,
                        colorScheme = scheme.toColorScheme(),
                        onClick = {
                            insertSetting(option.ordinal, scheme.ordinal)
                        }
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            LaunchedEffect(Unit) {
                if (colorScheme != null) state.scrollToItem(colorScheme.ordinal)
            }
        }
        Separator()
    }

    if (showColorPicker) {
        ColorDialog(
            color = Color(customAccent),
            onDismissRequest = { showColorPicker = false },
            onColorChange = { insertSetting(Setting.CUSTOM_ACCENT.ordinal, it) }
        )
    }
}

@Composable
fun SettingsState.ThemePreviewOption() {
    Column(Modifier.animateContentSize()) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        PreferenceRow(
            title = stringResource(R.string.theme_preview),
            icon = Icons.Outlined.Visibility,
            onClick = { expanded = !expanded }
        ) {
            RotationButton(
                rotated = expanded,
                onClick = { expanded = !expanded }
            )
        }
        if (expanded) {
            ColorSchemePreview()
            Spacer(modifier = Modifier.height(8.dp))
        }
        Separator()
    }
}

@Composable
fun SettingsState.DynamicColorsOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.DYNAMIC_COLORS
    PreferenceRowSwitch(
        title = stringResource(option.title),
        subtitle = option.subtitle?.let { stringResource(it) },
        icon = option.getIcon(nightMode),
        checked = dynamicColors,
        onClick = {
            insertSetting(option.ordinal, !dynamicColors)
        }
    )
    Separator()
}

@Composable
fun SettingsState.ChangeLanguageOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val context = LocalContext.current
    val dialogController = LocalDialogController.current
    val option = Setting.LANGUAGE

    Column(Modifier.animateContentSize()) {
        PreferenceRow(
            title = stringResource(option.title),
            icon = option.getIcon(nightMode),
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
                            insertSetting(option.ordinal, it)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsState.AppInfoVersionOption() {
    val toastHost = LocalToastHostState.current
    val context = LocalContext.current

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

@Composable
fun SettingsState.FontSizeOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.FONT_SCALE
    Column(Modifier.animateContentSize()) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        PreferenceRow(
            title = stringResource(option.title),
            subtitle = option.subtitle?.let { stringResource(it) },
            icon = option.getIcon(nightMode),
            onClick = { expanded = !expanded }
        ) {
            RotationButton(
                rotated = expanded,
                onClick = { expanded = !expanded }
            )
        }
        if (expanded) {
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = fontScale,
                onValueChange = {
                    insertSetting(option.ordinal, it)
                },
                valueRange = 0.75f..1.25f,
                steps = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Separator()
    }
}

@Composable
fun SettingsState.CartConnectionOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.CART_CONNECTION
    PreferenceRowSwitch(
        title = stringResource(option.title),
        subtitle = option.subtitle?.let { stringResource(it) },
        icon = option.getIcon(nightMode),
        checked = cartConnection,
        onClick = {
            insertSetting(option.ordinal, !cartConnection)
        }
    )
    Separator()
}

@Composable
fun SettingsState.PureBlackOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.PURE_BLACK
    PreferenceRowSwitch(
        title = stringResource(option.title),
        subtitle = option.subtitle?.let { stringResource(it) },
        icon = option.getIcon(nightMode),
        checked = pureBlack,
        onClick = {
            insertSetting(option.ordinal, !pureBlack)
        }
    )
    Separator()
}


@Composable
fun SettingsState.KeepScreenOnOption(insertSetting: (id: Int, option: Any) -> Unit) {
    val option = Setting.KEEP_SCREEN_ON
    PreferenceRowSwitch(
        title = stringResource(option.title),
        subtitle = option.subtitle?.let { stringResource(it) },
        icon = option.getIcon(nightMode),
        checked = keepScreenOn,
        onClick = {
            insertSetting(option.ordinal, !keepScreenOn)
        }
    )
    Separator()
}

@Composable
private fun AppThemeItem(
    title: String,
    icon: ImageVector = Icons.Filled.CheckCircle,
    colorScheme: ColorScheme,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(start = 8.dp, end = 8.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppThemePreviewItem(
            icon = icon,
            selected = selected,
            onClick = onClick,
            colorScheme = colorScheme,
            shapes = MaterialTheme.shapes
        )
        Spacer(Modifier.height(4.dp))
        Marquee {
            Text(
                modifier = it,
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
        }
    }
}