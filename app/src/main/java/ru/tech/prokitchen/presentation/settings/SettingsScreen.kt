package ru.tech.prokitchen.presentation.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.prokitchen.BuildConfig
import ru.tech.prokitchen.R
import ru.tech.prokitchen.presentation.app.components.NightMode
import ru.tech.prokitchen.presentation.app.components.Settings
import ru.tech.prokitchen.presentation.app.components.Settings.*
import ru.tech.prokitchen.presentation.app.components.SettingsState
import ru.tech.prokitchen.presentation.settings.components.ToggleGroup
import ru.tech.prokitchen.presentation.ui.theme.colorList

@ExperimentalFoundationApi
@Composable
fun SettingsScreen(settingsState: SettingsState, onAction: (Int, String) -> Unit) {
    LazyColumn {
        item { Spacer(Modifier.height(20.dp)) }
        itemsIndexed(values()) { _, setting ->
            Column(
                Modifier.animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var expandedNightMode by rememberSaveable { mutableStateOf(false) }
                var expandedColorScheme by rememberSaveable { mutableStateOf(false) }

                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(Modifier.width(20.dp))
                    Box(
                        Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Icon(
                            setting.getIcon(settingsState.nightMode),
                            null,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(stringResource(setting.title()), modifier = Modifier.weight(1f))
                    when (setting) {
                        CART_CONNECTION -> {
                            var checked by remember { mutableStateOf(settingsState.cartConnection) }
                            Spacer(Modifier.width(8.dp))
                            Switch(checked = checked, onCheckedChange = {
                                onAction(setting.ordinal, (!checked).toString())
                                checked = !checked
                            })
                            Spacer(Modifier.width(20.dp))
                        }
                        DYNAMIC_COLORS -> {
                            var checked by remember { mutableStateOf(settingsState.dynamicColors) }
                            Spacer(Modifier.width(8.dp))
                            Switch(checked = checked, onCheckedChange = {
                                onAction(setting.ordinal, (!checked).toString())
                                checked = !checked
                            })
                            Spacer(Modifier.width(20.dp))
                        }
                        NIGHT_MODE -> {
                            val rotation: Float by animateFloatAsState(if (expandedNightMode) 180f else 0f)
                            IconButton(
                                onClick = { expandedNightMode = !expandedNightMode },
                                modifier = Modifier.rotate(rotation)
                            ) {
                                Icon(
                                    Icons.Rounded.KeyboardArrowDown, null, modifier = Modifier
                                        .size(26.dp)
                                )
                            }
                        }
                        COLOR_SCHEME -> {
                            val rotation: Float by animateFloatAsState(if (expandedColorScheme) 180f else 0f)
                            IconButton(
                                onClick = { expandedColorScheme = !expandedColorScheme },
                                modifier = Modifier.rotate(rotation)
                            ) {
                                Icon(
                                    Icons.Rounded.KeyboardArrowDown, null, modifier = Modifier
                                        .size(26.dp)
                                )
                            }
                        }
                    }
                }
                if (expandedNightMode) {
                    ToggleGroup(
                        items = listOf(R.string.dark, R.string.light, R.string.system),
                        selectedIndex = settingsState.nightMode.ordinal,
                        indexChanged = {
                            onAction(setting.ordinal, it.toString())
                        })
                }
                if (expandedColorScheme) {
                    Spacer(Modifier.height(10.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 30.dp)
                    ) {
                        itemsIndexed(colorList) { index, item ->
                            Box {
                                OutlinedButton(
                                    onClick = { onAction(setting.ordinal, index.toString()) },
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        1.dp, item.md_theme_light_primary
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = item.md_theme_light_primaryContainer,
                                        contentColor = item.md_theme_light_primary
                                    ),
                                    modifier = Modifier
                                        .size(50.dp)
                                ) {}
                                if (settingsState.colorScheme.ordinal == index) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        null,
                                        tint = item.md_theme_light_primary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                            Spacer(Modifier.width(5.dp))
                        }
                    }
                }
                setting.subtitle()?.also { subtitle ->
                    Spacer(Modifier.height(10.dp))
                    Text(
                        stringResource(subtitle),
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
        item {
            val context = LocalContext.current
            Spacer(Modifier.height(80.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .size(86.dp)
                            .shadow(1.dp, RoundedCornerShape(20.dp))
                            .combinedClickable(onLongClick = {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/T8RIN/PROkitchen"))
                                )
                            }, onClick = {})
                            .background(Brush.verticalGradient(colors = listOf(Color(0xFF00EBCE), Color(0xFF00ABFF))))
                            .clip(RoundedCornerShape(20.dp))
                            .align(Alignment.Center),
                        content = {}
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .size(114.dp)
                            .align(Alignment.Center)
                    )
                }
                Text(stringResource(R.string.app_name))
                Text(
                    "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}

fun Settings.getIcon(nightMode: NightMode): ImageVector {
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

fun Settings.title(): Int {
    return when (this) {
        NIGHT_MODE -> R.string.nightMode
        DYNAMIC_COLORS -> R.string.dynamicColors
        COLOR_SCHEME -> R.string.colorScheme
        CART_CONNECTION -> R.string.cartConnection
    }
}

fun Settings.subtitle(): Int? {
    return when (this) {
        NIGHT_MODE -> null
        DYNAMIC_COLORS -> R.string.dynamicColorsSubtitle
        COLOR_SCHEME -> null
        CART_CONNECTION -> R.string.cartConnectionSubtitle
    }
}