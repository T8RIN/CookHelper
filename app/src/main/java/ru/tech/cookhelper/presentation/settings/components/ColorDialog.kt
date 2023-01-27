package ru.tech.cookhelper.presentation.settings.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.maxkeppeker.sheets.core.utils.TestTags
import com.maxkeppeker.sheets.core.utils.testSequenceTagOf
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import kotlinx.coroutines.delay
import ru.tech.cookhelper.R
import com.maxkeppeler.sheets.color.R as R1

@ExperimentalMaterial3Api
@Composable
fun ColorDialog(
    color: Color?,
    onColorChange: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var _color by rememberSaveable { mutableStateOf(color?.toArgb() ?: Color.Red.toArgb()) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.color_scheme)) },
        icon = { Icon(Icons.Outlined.Palette, null) },
        text = {
            MaterialTheme(shapes = Shapes()) {
                ColorCustomComponent(
                    config = ColorConfig(
                        allowCustomColorAlphaValues = false,
                        defaultDisplayMode = ColorSelectionMode.CUSTOM
                    ),
                    color = _color,
                    onColorChange = { _color = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onColorChange(_color)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun ColorCustomComponent(
    config: ColorConfig,
    color: Int,
    onColorChange: (Int) -> Unit,
) {
    Column {
        ColorCustomInfoComponent(
            config = config,
            color = color,
            onColorChange = onColorChange,
        )
        ColorCustomControlComponent(
            config = config,
            color = color,
            onColorChange = onColorChange
        )
    }
}

/** Save a text into the clipboard. */
fun copyColorIntoClipboard(ctx: Context, label: String, value: String) {
    val clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
}

/** Receive the clipboard data. */
fun pasteColorFromClipboard(
    ctx: Context,
    onPastedColor: (Int) -> Unit,
    onPastedColorFailure: (String) -> Unit,
) {
    val clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val item = clipboard.primaryClip?.getItemAt(0)
    val text = item?.text?.toString()
    text?.let {
        runCatching {
            // Color detected
            onPastedColor(android.graphics.Color.parseColor(it))
        }.getOrElse {
            // Clipboard information can not be parsed to color
            onPastedColorFailure(ctx.getString(R.string.scd_color_dialog_clipboard_paste_invalid_color_code))
        }
    } ?: run {
        // Clipboard was empty
        onPastedColorFailure(ctx.getString(R.string.scd_color_dialog_clipboard_paste_invalid_empty))
    }
}

/** Receive the clipboard data. */
fun getFormattedColor(color: Int): String =
    String.format("#%08X", (0xFFFFFFFF and color.toLong()))


@Composable
private fun ColorCustomInfoComponent(
    config: ColorConfig,
    color: Int,
    onColorChange: (Int) -> Unit,
) {
    val context = LocalContext.current
    val colorPasteError = rememberSaveable { mutableStateOf<String?>(null) }
    val onCopyCustomColor = {
        copyColorIntoClipboard(
            ctx = context,
            label = context.getString(R1.string.scd_color_dialog_color),
            value = getFormattedColor(color)
        )
    }
    val onPasteCustomColor = {
        pasteColorFromClipboard(
            ctx = context,
            onPastedColor = { onColorChange(it) },
            onPastedColorFailure = { colorPasteError.value = it },
        )
    }
    LaunchedEffect(colorPasteError.value) {
        delay(3000)
        colorPasteError.value = null
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ColorTemplateItemComponent(
            modifier = Modifier
                .size(56.dp),
            color = color,
            onColorClick = onColorChange
        )

        ElevatedCard(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(start = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_normal_100))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_normal_100))
                    .padding(end = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_small_100)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(start = 0.dp)) {
                    if (colorPasteError.value != null) {
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = colorPasteError.value!!,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    } else {
                        Text(
                            text = stringResource(R1.string.scd_color_dialog_argb),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier.padding(top = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_small_25)),
                            text = getFormattedColor(color),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    }
                }
                if (colorPasteError.value == null) {
                    Spacer(modifier = Modifier.weight(1f))
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.background),
                        modifier = Modifier,
                        onClick = onCopyCustomColor
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_size_150)),
                            imageVector = config.icons.ContentCopy,
                            contentDescription = stringResource(R1.string.scd_color_dialog_copy_color),
                        )
                    }
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.background),
                        modifier = Modifier,
                        onClick = onPasteCustomColor
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_size_150)),
                            imageVector = config.icons.ContentPaste,
                            contentDescription = stringResource(R1.string.scd_color_dialog_paste_color),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColorTemplateItemComponent(
    modifier: Modifier = Modifier,
    color: Int,
    inputDisabled: Boolean = false,
    onColorClick: (Int) -> Unit
) {
    val colorShape = MaterialTheme.shapes.medium
    Box(modifier = modifier.aspectRatio(1f)) {
        ElevatedCard(
            modifier = Modifier
                .align(Alignment.Center)
                .aspectRatio(1f),
            shape = colorShape,
            onClick = { onColorClick(color) },
            enabled = !inputDisabled
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R1.drawable.scd_color_dialog_transparent_pattern),
                    contentDescription = null,
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(color))
                ) {
                }
            }
        }
    }
}

@Composable
private fun ColorCustomControlComponent(
    config: ColorConfig,
    color: Int,
    onColorChange: (Int) -> Unit
) {

    val alphaValue = remember(color) {
        val value = if (config.allowCustomColorAlphaValues) color.alpha else 255
        mutableStateOf(value)
    }
    val redValue = remember(color) { mutableStateOf(color.red) }
    val greenValue = remember(color) { mutableStateOf(color.green) }
    val blueValue = remember(color) { mutableStateOf(color.blue) }

    val newColor by remember(alphaValue.value, redValue.value, greenValue.value, blueValue.value) {
        mutableStateOf(Color(redValue.value, greenValue.value, blueValue.value, alphaValue.value))
    }

    LaunchedEffect(newColor) { onColorChange.invoke(newColor.toArgb()) }

    val colorItemLabelWidth = remember { mutableStateOf<Int?>(null) }
    val colorValueLabelWidth = remember { mutableStateOf<Int?>(null) }

    val colorItems = mutableListOf(
        stringResource(R1.string.scd_color_dialog_alpha) to alphaValue,
        stringResource(R.string.scd_color_dialog_red) to redValue,
        stringResource(R.string.scd_color_dialog_green) to greenValue,
        stringResource(R.string.scd_color_dialog_blue) to blueValue
    )

    Column(modifier = Modifier.padding(top = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_small_150))) {
        colorItems.forEachIndexed { index, entry ->
            if ((config.allowCustomColorAlphaValues)
                || (!config.allowCustomColorAlphaValues && index > 0)
            ) {
                ColorCustomControlItemComponent(
                    label = entry.first,
                    value = entry.second.value,
                    onValueChange = { entry.second.value = it },
                    colorItemLabelWidth = colorItemLabelWidth,
                    colorValueLabelWidth = colorValueLabelWidth,
                    sliderTestTag = testSequenceTagOf(
                        TestTags.COLOR_CUSTOM_VALUE_SLIDER,
                        index.toString()
                    ),
                )
            }
        }
    }
}

@Composable
private fun ColorCustomControlItemComponent(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    colorItemLabelWidth: MutableState<Int?>,
    colorValueLabelWidth: MutableState<Int?>,
    sliderTestTag: String,
) {
    val wrapOrFixedModifier: @Composable (MutableState<Int?>) -> Modifier = { stateWidth ->
        val defaultModifier = Modifier
            .wrapContentWidth()
            .onGloballyPositioned { coordinates ->
                if ((stateWidth.value ?: 0) < coordinates.size.width) {
                    stateWidth.value = coordinates.size.width
                }
            }
        stateWidth.value?.let {
            Modifier.width(LocalDensity.current.run { it.toDp() })
        } ?: defaultModifier
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = wrapOrFixedModifier(colorItemLabelWidth),
            text = label,
            style = MaterialTheme.typography.labelMedium,
        )

        Slider(
            modifier = Modifier
                .testTag(sliderTestTag)
                .weight(1f)
                .padding(horizontal = dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_normal_100)),
            valueRange = 0f..255f,
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
        )

        Text(
            modifier = wrapOrFixedModifier(colorValueLabelWidth),
            text = value.toString(),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.End,
        )
    }
}