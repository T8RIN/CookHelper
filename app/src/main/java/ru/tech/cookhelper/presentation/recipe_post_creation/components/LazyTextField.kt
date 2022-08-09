package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String,
    shape: Shape = RoundedCornerShape(4.dp),
    startIcon: ImageVector,
    formatText: String.() -> String = { this },
    textStyle: TextStyle = LocalTextStyle.current,
    onLoseFocusTransformation: String.() -> String = { this },
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    singleLine: Boolean = true
) {
    var value by rememberSaveable { mutableStateOf("") }
    val color = remember { Animatable(initialValue = Color.Transparent) }
    val colorScheme = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()

    TextField(
        modifier = modifier
            .border(
                width = 2.dp,
                color = color.value,
                shape = shape
            )
            .onFocusChanged {
                scope.launch {
                    if (it.isFocused) color.animateTo(colorScheme.primary)
                    else {
                        color.animateTo(Color.Transparent)
                        value = value.onLoseFocusTransformation()
                    }
                    cancel()
                }
            }
            .animateContentSize(),
        value = value,
        textStyle = textStyle,
        onValueChange = {
            value = it.formatText()
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = shape,
        label = { Text(label, modifier = if (singleLine) Modifier else Modifier.offset(4.dp)) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(startIcon, null) }
    )

    LaunchedEffect(value) {
        onValueChange(value)
    }
}