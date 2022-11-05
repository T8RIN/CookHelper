package ru.tech.cookhelper.presentation.forum_screen.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle(
        fontSize = 22.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Start,
    ),
    hint: @Composable () -> Unit,
    onValueChange: (String) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value,
        textStyle = textStyle,
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        onValueChange = onValueChange
    )
    if (value.isEmpty()) {
        hint()
    } else {
        BackHandler {
            onValueChange("")
            localFocusManager.clearFocus()
        }
    }
}