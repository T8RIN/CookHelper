package ru.tech.cookhelper.presentation.recipe_post_creation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String,
    startIcon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    singleLine: Boolean = true,
) {
    var value by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
            value = it
        },
        label = { Text(label) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            startIcon?.let {
                Icon(startIcon, null)
            }
        }
    )
}