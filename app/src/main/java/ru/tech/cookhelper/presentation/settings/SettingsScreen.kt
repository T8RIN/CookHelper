package ru.tech.cookhelper.presentation.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.sp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.BuildConfig
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.Toast
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.settings.components.*
import ru.tech.cookhelper.presentation.settings.components.Setting.*
import ru.tech.cookhelper.presentation.settings.viewModel.SettingsViewModel
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.theme.colorList
import ru.tech.cookhelper.presentation.ui.theme.ordinal
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
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
        contentPadding = WindowInsets.navigationBars.asPaddingValues().addPadding(top = 20.dp)
    ) {
        items(Setting.values(), key = { it.name }) { setting ->
            Column(
                modifier = Modifier.animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var expandedNightMode by rememberSaveable { mutableStateOf(false) }
                var expandedColorScheme by rememberSaveable { mutableStateOf(false) }
                var onClick by remember { mutableStateOf({}) }

                val alphaModifier = Modifier.alpha(
                    if (setting == COLOR_SCHEME) {
                        if (!settingsState.dynamicColors) 1f
                        else 0.5f
                    } else 1f
                )

                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick() }
                        .then(alphaModifier)
                ) {
                    Spacer(Modifier.width(20.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = SquircleShape(14.dp),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Icon(
                                setting.getIcon(settingsState.nightMode),
                                null,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(
                        stringResource(setting.title),
                        modifier = Modifier.weight(1f)
                    )
                    when (setting) {
                        CART_CONNECTION -> {
                            Switch(
                                checked = settingsState.cartConnection,
                                setting = setting,
                                viewModel = viewModel
                            )
                        }
                        DYNAMIC_COLORS -> {
                            Switch(
                                checked = settingsState.dynamicColors,
                                setting = setting,
                                viewModel = viewModel
                            )
                        }
                        NIGHT_MODE -> {
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
                            onClick = { expandedNightMode = !expandedNightMode }
                        }
                        COLOR_SCHEME -> {
                            val rotation: Float by animateFloatAsState(if (expandedColorScheme && !settingsState.dynamicColors) 180f else 0f)
                            IconButton(
                                onClick = { expandedColorScheme = !expandedColorScheme },
                                modifier = Modifier.rotate(rotation),
                                enabled = !settingsState.dynamicColors
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            onClick = {
                                if (!settingsState.dynamicColors) {
                                    expandedColorScheme = !expandedColorScheme
                                } else toastHost.sendToast(
                                    Icons.Outlined.InvertColors,
                                    (R.string.cannot_change_theme).asString(context)
                                )
                            }
                        }
                    }
                }
                if (expandedNightMode) {
                    ToggleGroupButton(
                        items = listOf(R.string.dark, R.string.light, R.string.system),
                        selectedIndex = settingsState.nightMode.ordinal,
                        indexChanged = {
                            viewModel.insertSetting(setting.ordinal, it)
                        }
                    )
                }
                if (expandedColorScheme && !settingsState.dynamicColors) {
                    Spacer(Modifier.height(10.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 30.dp)
                    ) {
                        itemsIndexed(colorList, key = { _, cs -> cs.ordinal }) { index, item ->
                            Box {
                                OutlinedButton(
                                    onClick = { viewModel.insertSetting(setting.ordinal, index) },
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        1.5.dp, item.md_theme_dark_primaryContainer
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = item.md_theme_dark_onPrimaryContainer,
                                        contentColor = item.md_theme_dark_primaryContainer
                                    ),
                                    modifier = Modifier.size(50.dp)
                                ) {}
                                androidx.compose.animation.AnimatedVisibility(
                                    visible = settingsState.colorScheme.ordinal == index,
                                    modifier = Modifier.align(Alignment.Center),
                                    enter = fadeIn() + scaleIn(),
                                    exit = fadeOut() + scaleOut()
                                ) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        null,
                                        tint = item.md_theme_light_primary,
                                    )
                                }
                            }
                            Spacer(Modifier.width(5.dp))
                        }
                    }
                    ColorSchemePreview()
                }
                setting.subtitle?.apply {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        stringResource(this),
                        fontSize = 12.sp,
                        color = Gray,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Separator()
            }
        }
        item {
            Spacer(Modifier.height(80.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        modifier = Modifier
                            .size(86.dp)
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
                                    toastHost.sendToast(
                                        Icons.Outlined.GetApp,
                                        (R.string.long_click_to_go).asString(context),
                                        Toast.Short.time
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
                            .size(114.dp)
                            .align(Alignment.Center),
                        shimmerEnabled = false
                    )
                }
                Text(stringResource(R.string.app_name))
                Text(
                    "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    fontSize = 12.sp,
                    color = Gray
                )
            }
            Spacer(Modifier.height(80.dp))
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
    }
}

val Setting.title: Int
    get() {
        return when (this) {
            NIGHT_MODE -> R.string.app_theme_mode
            DYNAMIC_COLORS -> R.string.dynamic_сolors
            COLOR_SCHEME -> R.string.color_scheme
            CART_CONNECTION -> R.string.cart_connection
        }
    }

val Setting.subtitle: Int?
    get() {
        return when (this) {
            NIGHT_MODE -> null
            DYNAMIC_COLORS -> R.string.dynamic_colors_subtitle
            COLOR_SCHEME -> null
            CART_CONNECTION -> R.string.cart_connection_subtitle
        }
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun RowScope.Switch(
    checked: Boolean,
    viewModel: SettingsViewModel,
    setting: Setting
) {
    Spacer(Modifier.width(8.dp))
    Switch(
        checked = checked,
        onCheckedChange = {
            viewModel.insertSetting(
                setting.ordinal,
                !checked
            )
        },
        thumbContent = {
            AnimatedContent(targetState = checked) { chk ->
                Icon(
                    if (chk) Icons.Filled.Done else Icons.Filled.Close,
                    null,
                    Modifier.size(16.dp)
                )
            }
        }
    )
    Spacer(Modifier.width(20.dp))
}