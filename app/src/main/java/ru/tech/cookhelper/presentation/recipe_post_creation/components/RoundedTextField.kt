package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.blend
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.shimmer
import ru.tech.cookhelper.presentation.ui.utils.provide

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String = "",
    hint: String = "",
    shape: Shape = RoundedCornerShape(4.dp),
    startIcon: ImageVector? = null,
    value: String,
    isError: Boolean = false,
    loading: Boolean = false,
    error: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    formatText: String.() -> String = { this },
    textStyle: TextStyle = LocalTextStyle.current,
    onLoseFocusTransformation: String.() -> String = { this },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    colors: TextFieldColors = RoundedTextFieldColors(isError),
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val labelImpl = @Composable {
        Text(
            text = label,
            modifier = if (singleLine) Modifier else Modifier.offset(y = 4.dp)
        )
    }
    val hintImpl = @Composable {
        Text(text = hint, modifier = Modifier.padding(start = 4.dp))
    }
    val leadingIconImpl = @Composable {
        Icon(
            imageVector = startIcon!!,
            contentDescription = null
        )
    }

    RoundedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it.formatText()) },
        textStyle = textStyle,
        colors = colors,
        shape = shape,
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        endIcon = endIcon,
        startIcon = if (startIcon != null) leadingIconImpl else null,
        label = if (label.isNotEmpty()) labelImpl else null,
        hint = if (hint.isNotEmpty()) hintImpl else null,
        isError = isError,
        loading = loading,
        error = error,
        formatText = formatText,
        onLoseFocusTransformation = onLoseFocusTransformation,
        keyboardActions = keyboardActions,
        enabled = enabled,
        maxLines = maxLines,
        interactionSource = interactionSource
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: (@Composable () -> Unit)? = null,
    hint: (@Composable () -> Unit)? = null,
    shape: Shape = RoundedCornerShape(4.dp),
    startIcon: (@Composable () -> Unit)? = null,
    value: String,
    isError: Boolean = false,
    loading: Boolean = false,
    error: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    formatText: String.() -> String = { this },
    textStyle: TextStyle = LocalTextStyle.current,
    onLoseFocusTransformation: String.() -> String = { this },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    colors: TextFieldColors = RoundedTextFieldColors(isError),
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val focus = LocalFocusManager.current

    val borderColor = remember { Animatable(initialValue = Color.Transparent) }
    val colorScheme = MaterialTheme.colorScheme

    val focused = interactionSource.collectIsFocusedAsState().value

    val scope = rememberCoroutineScope()
    LaunchedEffect(isError) {
        borderColor.animateTo(if (isError && focused) colorScheme.error else if (focused) colorScheme.primary else Color.Transparent)
    }

    val mergedModifier = Modifier
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = borderColor.value,
            shape = shape
        )
        .onFocusChanged {
            scope.launch {
                if (readOnly) {
                    focus.clearFocus()
                    cancel()
                }
                if (it.isFocused) borderColor.animateTo(if (isError) colorScheme.error else colorScheme.primary)
                else {
                    if (!isError) borderColor.animateTo(Color.Transparent)
                    onValueChange(value.onLoseFocusTransformation())
                }
                cancel()
            }
        }
        .animateContentSize()

    Column(
        modifier = modifier
            .animateContentSize()
            .clip(shape)
            .shimmer(
                visible = loading,
                color = MaterialTheme.colorScheme.surfaceVariant.createSecondaryColor(0.1f)
            )
    ) {
        TextField(
            modifier = mergedModifier,
            value = value,
            onValueChange = { onValueChange(it.formatText()) },
            textStyle = textStyle,
            colors = colors,
            shape = shape,
            singleLine = singleLine,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            trailingIcon = endIcon,
            leadingIcon = startIcon,
            label = label,
            placeholder = hint,
            keyboardActions = keyboardActions,
            enabled = enabled,
            maxLines = maxLines,
            interactionSource = interactionSource
        )
        if (isError && !loading && error != null) {
            Spacer(Modifier.height(6.dp))
            ProvideTextStyle(
                LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            ) {
                Row {
                    Spacer(modifier = Modifier.width(15.dp))
                    error()
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedTextFieldColors(isError: Boolean): TextFieldColors =
    MaterialTheme.colorScheme.provide {
        TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = if (isError) error else primary,
            focusedLabelColor = if (isError) error else primary,
            focusedLeadingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedLeadingIconColor = if (isError) error else onSurfaceVariant,
            focusedTrailingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedTrailingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedLabelColor = if (isError) error else onSurfaceVariant,
            containerColor = if (isError) surfaceVariant.blend(error, 0.2f) else surfaceVariant
        )
    }