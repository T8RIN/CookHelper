package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.tech.cookhelper.presentation.recipe_post_creation.components.RoundedTextField
import ru.tech.cookhelper.presentation.recipe_post_creation.components.RoundedTextFieldColors
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.createSecondaryColor
import ru.tech.cookhelper.presentation.ui.utils.compose.shimmer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CozyTextField(
    modifier: Modifier = Modifier,
    appearance: TextFieldAppearance = TextFieldAppearance.Rounded,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    hint: String? = null,
    shape: Shape = when (appearance) {
        TextFieldAppearance.Rounded -> RoundedCornerShape(4.dp)
        TextFieldAppearance.Outlined -> TextFieldDefaults.outlinedShape
        TextFieldAppearance.Filled -> TextFieldDefaults.filledShape
    },
    startIcon: ImageVector? = null,
    isError: Boolean = false,
    loading: Boolean = false,
    error: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    formatText: String.() -> String = { this },
    textStyle: TextStyle = LocalTextStyle.current,
    onLoseFocusTransformation: String.() -> String = { this },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    colors: TextFieldColors = when (appearance) {
        TextFieldAppearance.Rounded -> RoundedTextFieldColors(isError)
        TextFieldAppearance.Outlined -> TextFieldDefaults.outlinedTextFieldColors()
        TextFieldAppearance.Filled -> TextFieldDefaults.textFieldColors()
    },
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val labelImpl = @Composable {
        Text(text = label!!)
    }
    val hintImpl = @Composable {
        Text(text = hint!!)
    }
    val leadingIconImpl = @Composable {
        Icon(
            imageVector = startIcon!!,
            contentDescription = null
        )
    }
    CozyTextField(
        modifier = modifier,
        appearance = appearance,
        value = value,
        onValueChange = onValueChange,
        label = if (label != null) labelImpl else null,
        hint = if (hint != null) hintImpl else null,
        shape = shape,
        startIcon = if (startIcon != null) leadingIconImpl else null,
        isError = isError,
        loading = loading,
        error = error,
        endIcon = endIcon,
        formatText = formatText,
        textStyle = textStyle,
        onLoseFocusTransformation = onLoseFocusTransformation,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        readOnly = readOnly,
        colors = colors,
        enabled = enabled,
        maxLines = maxLines,
        interactionSource = interactionSource
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CozyTextField(
    modifier: Modifier = Modifier,
    appearance: TextFieldAppearance = TextFieldAppearance.Rounded,
    value: String,
    onValueChange: (String) -> Unit,
    label: (@Composable () -> Unit)? = null,
    hint: (@Composable () -> Unit)? = null,
    shape: Shape = when (appearance) {
        TextFieldAppearance.Rounded -> RoundedCornerShape(4.dp)
        TextFieldAppearance.Outlined -> TextFieldDefaults.outlinedShape
        TextFieldAppearance.Filled -> TextFieldDefaults.filledShape
    },
    startIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    loading: Boolean = false,
    error: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    formatText: String.() -> String = { this },
    textStyle: TextStyle = LocalTextStyle.current,
    onLoseFocusTransformation: String.() -> String = { this },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    colors: TextFieldColors = when (appearance) {
        TextFieldAppearance.Rounded -> RoundedTextFieldColors(isError)
        TextFieldAppearance.Outlined -> TextFieldDefaults.outlinedTextFieldColors()
        TextFieldAppearance.Filled -> TextFieldDefaults.textFieldColors()
    },
    enabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val focus = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val mergedModifier = Modifier
        .fillMaxWidth()
        .onFocusChanged {
            scope.launch {
                if (readOnly) {
                    focus.clearFocus()
                    cancel()
                }
                if (!it.isFocused) onValueChange(value.onLoseFocusTransformation())
                cancel()
            }
        }
        .animateContentSize()
    val errorImpl = @Composable {
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

    when (appearance) {
        TextFieldAppearance.Outlined -> {
            Column(
                modifier = modifier
                    .animateContentSize()
                    .clip(shape)
                    .shimmer(visible = loading)
            ) {
                OutlinedTextField(
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
                    interactionSource = interactionSource,
                    isError = isError
                    //TODO: Try to use new supportingText feature
                )
                errorImpl()
            }
        }
        TextFieldAppearance.Filled -> {
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
                    interactionSource = interactionSource,
                    isError = isError,
                )
                errorImpl()
            }
        }
        TextFieldAppearance.Rounded -> {
            RoundedTextField(
                modifier = modifier,
                onValueChange = onValueChange,
                label = label,
                hint = hint,
                shape = shape,
                startIcon = startIcon,
                value = value,
                isError = isError,
                loading = loading,
                error = error,
                endIcon = endIcon,
                formatText = formatText,
                textStyle = textStyle,
                onLoseFocusTransformation = onLoseFocusTransformation,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                readOnly = readOnly,
                colors = colors,
                enabled = enabled,
                maxLines = maxLines,
                interactionSource = interactionSource
            )
        }
    }
}

enum class TextFieldAppearance { Outlined, Filled, Rounded }