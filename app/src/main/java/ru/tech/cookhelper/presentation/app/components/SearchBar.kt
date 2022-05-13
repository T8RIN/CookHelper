package ru.tech.cookhelper.presentation.app.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(searchString: String, onValueChange: (String) -> Unit) {
    val localFocusManager =
        LocalFocusManager.current
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchString,
        textStyle = TextStyle(
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
        ),
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        onValueChange = {
            onValueChange(it)
        })
    if (searchString.isEmpty()) {
        Text(
            text = "Монда изләгез",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            style = TextStyle(
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Start,
            )
        )
    } else {
        BackHandler {
            onValueChange("")
            localFocusManager.clearFocus()
        }
    }
}