package ru.tech.cookhelper.presentation.settings.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.utils.TestTags
import com.maxkeppeker.sheets.core.utils.testSequenceTagOf
import com.maxkeppeker.sheets.core.utils.testTags
import com.maxkeppeker.sheets.core.views.BaseTypeState
import com.maxkeppeker.sheets.core.views.ButtonsComponent
import com.maxkeppeker.sheets.core.views.base.FrameBase
import com.maxkeppeler.sheets.color.models.ColorConfig
import com.maxkeppeler.sheets.color.models.ColorSelection
import com.maxkeppeler.sheets.color.models.ColorSelectionMode
import kotlinx.coroutines.delay
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape
import java.io.Serializable
import com.maxkeppeler.sheets.color.R as R1

@ExperimentalMaterial3Api
@Composable
fun ColorDialog(
    state: SheetState,
    selection: ColorSelection,
    config: ColorConfig = ColorConfig(),
    header: Header? = null,
    properties: DialogProperties = DialogProperties(),
) {
    MaterialTheme(shapes = Shapes()) {
        DialogBase(
            state = state,
            properties = properties,
        ) {
            val context = LocalContext.current
            val colorState = rememberColorState(context, selection, config)
            StateHandler(state, colorState)


            FrameBase(
                header = header,
                content = {
                    ColorCustomComponent(
                        config = config,
                        color = colorState.color ?: Color.Gray.toArgb(),
                        onColorChange = colorState::processSelection,
                    )
                }
            ) {
                ButtonsComponent(
                    selection = selection,
                    onPositiveValid = colorState.valid,
                    onNegative = { selection.onNegativeClick?.invoke() },
                    onPositive = colorState::onFinish,
                    onClose = state::finish
                )
            }
        }
    }
}

@Composable
fun StateHandler(
    sheetState: SheetState,
    baseState: BaseTypeState,
) {
    DisposableEffect(sheetState.reset) {
        if (sheetState.reset) {
            baseState.reset()
            sheetState.clearReset()
        }
        onDispose {}
    }
}

@Composable
private fun DialogBase(
    state: SheetState = rememberSheetState(false),
    properties: DialogProperties = DialogProperties(),
    onDialogClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(Unit) {
        state.markAsEmbedded()
    }

    if (!state.visible) return

    val boxInteractionSource = remember { MutableInteractionSource() }
    val contentInteractionSource = remember { MutableInteractionSource() }

    Dialog(
        onDismissRequest = state::dismiss,
        properties = properties,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .testTag(TestTags.DIALOG_BASE_CONTAINER)
                .fillMaxSize()
                .clickable(
                    interactionSource = boxInteractionSource,
                    indication = null,
                    onClick = state::dismiss
                )
        ) {
            Surface(
                modifier = Modifier
                    .testTag(TestTags.DIALOG_BASE_CONTENT)
                    .fillMaxWidth()
                    .animateContentSize()
                    .clickable(
                        indication = null,
                        interactionSource = contentInteractionSource,
                        onClick = { onDialogClick?.invoke() }
                    ),
                shape = DialogShape,
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
                content = content
            )
        }
    }
}

class SheetState(
    visible: Boolean = false,
    embedded: Boolean = true,
    internal val onFinishedRequest: (SheetState.() -> Unit)? = null,
    internal val onDismissRequest: (SheetState.() -> Unit)? = null,
    internal val onCloseRequest: (SheetState.() -> Unit)? = null,
) {
    var visible by mutableStateOf(visible)
    var embedded by mutableStateOf(embedded)
    var reset by mutableStateOf(false)

    /**
     * Display the dialog / view.
     */
    fun show() {
        visible = true
    }

    /**
     * Hide the dialog / view.
     */
    fun hide() {
        visible = false
        onDismissRequest?.invoke(this)
        onCloseRequest?.invoke(this)
    }

    internal fun clearReset() {
        reset = false
    }

    /**
     * Reset the current state data.
     */
    fun invokeReset() {
        reset = true
    }

    fun dismiss() {
        if (!embedded) visible = false
        onDismissRequest?.invoke(this)
        onCloseRequest?.invoke(this)
    }

    /**
     * Finish the use-case view.
     */
    fun finish() {
        /*
            We don't want to remove the view itself,
            but inform through the state that the use-case is done.
            The parent container (Dialog, PopUp, BottomSheet)
            can be hidden with the use-case view.
         */
        if (!embedded) visible = false
        onFinishedRequest?.invoke(this)
        onCloseRequest?.invoke(this)
    }

    internal fun markAsEmbedded() {
        embedded = false
    }

    companion object {

        /**
         * [Saver] implementation.
         * Lambda functions need to be passed to new sheet state as they can not be serialized.
         * @param onCloseRequest The listener that is invoked when the dialog was closed through any cause.
         * @param onFinishedRequest The listener that is invoked when the dialog's use-case was finished by the user accordingly (negative, positive, selection).
         * @param onDismissRequest The listener that is invoked when the dialog was dismissed.
         */
        fun Saver(
            onCloseRequest: (SheetState.() -> Unit)?,
            onFinishedRequest: (SheetState.() -> Unit)?,
            onDismissRequest: (SheetState.() -> Unit)?
        ): Saver<SheetState, *> = Saver(
            save = { state ->
                SheetStateData(
                    visible = state.visible,
                    embedded = state.embedded,
                )
            },
            restore = { data ->
                SheetState(
                    visible = data.visible,
                    embedded = data.embedded,
                    onCloseRequest = onCloseRequest,
                    onFinishedRequest = onFinishedRequest,
                    onDismissRequest = onDismissRequest,
                )
            }
        )
    }

    /**
     * Data class that stores the important information of the current state
     * and can be used by the [Saver] to save and restore the state.
     */
    data class SheetStateData(
        val visible: Boolean,
        val embedded: Boolean,
    ) : Serializable
}

/**
 * Create a SheetState and remember it.
 * @param visible The initial visibility.
 * @param embedded if the use-case is embedded in a container (dialog, bottomSheet, popup, ...)
 * @param onCloseRequest The listener that is invoked when the dialog was closed through any cause.
 * @param onFinishedRequest The listener that is invoked when the dialog's use-case was finished by the user accordingly (negative, positive, selection).
 * @param onDismissRequest The listener that is invoked when the dialog was dismissed.
 */
@Composable
fun rememberSheetState(
    visible: Boolean = false,
    embedded: Boolean = true,
    onCloseRequest: (SheetState.() -> Unit)? = null,
    onFinishedRequest: (SheetState.() -> Unit)? = null,
    onDismissRequest: (SheetState.() -> Unit)? = null,
): SheetState = rememberSaveable(
    saver = SheetState.Saver(
        onCloseRequest = onCloseRequest,
        onFinishedRequest = onFinishedRequest,
        onDismissRequest = onDismissRequest
    ),
    init = {
        SheetState(
            visible = visible,
            embedded = embedded,
            onCloseRequest = onCloseRequest,
            onFinishedRequest = onFinishedRequest,
            onDismissRequest = onDismissRequest
        )
    }
)

private class ColorState(
    private val context: Context,
    val selection: ColorSelection,
    val config: ColorConfig = ColorConfig(),
    stateData: ColorStateData? = null,
) : BaseTypeState() {

    var color by mutableStateOf(stateData?.color ?: getInitColor())
    val colors by mutableStateOf(getInitColors())
    var displayMode by mutableStateOf(stateData?.displayMode ?: getInitDisplayMode())
    var valid by mutableStateOf(isValid())

    private fun getInitColor(): Int? =
        selection.selectedColor?.colorInInt(context)

    private fun getInitColors(): List<Int> =
        config.templateColors.getColorsAsInt(context)

    private fun getInitDisplayMode(): ColorSelectionMode =
        config.defaultDisplayMode.takeUnless {
            config.displayMode != null && config.displayMode != config.defaultDisplayMode
        } ?: config.displayMode ?: ColorSelectionMode.TEMPLATE

    private fun checkValid() {
        valid = isValid()
    }

    fun processSelection(newColor: Int) {
        color = newColor
        checkValid()
    }

    private fun isValid(): Boolean = color != null

    fun onFinish() {
        selection.onSelectColor(color!!)
    }

    override fun reset() {
        color = getInitColor()
    }

    companion object {

        /**
         * [Saver] implementation.
         * @param context The context that is used to resolve the colors.
         * @param selection The selection configuration for the dialog view.
         * @param config The general configuration for the dialog view.
         */
        fun Saver(
            context: Context,
            selection: ColorSelection,
            config: ColorConfig
        ): Saver<ColorState, *> = Saver(
            save = { state -> ColorStateData(state.color, state.displayMode) },
            restore = { data -> ColorState(context, selection, config, data) }
        )
    }

    /**
     * Data class that stores the important information of the current state
     * and can be used by the [Saver] to save and restore the state.
     */
    data class ColorStateData(
        val color: Int?,
        val displayMode: ColorSelectionMode
    ) : Serializable
}

@Composable
private fun rememberColorState(
    context: Context,
    selection: ColorSelection,
    config: ColorConfig,
): ColorState = rememberSaveable(
    inputs = arrayOf(selection, config),
    saver = ColorState.Saver(context, selection, config),
    init = { ColorState(context, selection, config) }
)

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
            config = config,
            modifier = Modifier
                .testTags(TestTags.COLOR_CUSTOM_SELECTION, color)
                .size(56.dp),
            color = color,
            selected = false,
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
    config: ColorConfig,
    modifier: Modifier = Modifier,
    color: Int,
    selected: Boolean,
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
        if (selected) {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_size_150)),
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(com.maxkeppeler.sheets.core.R.dimen.scd_size_100)),
                    imageVector = config.icons.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else if (color.alpha < 255) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(dimensionResource(id = com.maxkeppeler.sheets.core.R.dimen.scd_small_25)),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                text = "${color.alpha.toFloat().div(255).times(100).toInt()}%"
            )
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