package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmationField(
    length: Int,
    codeState: CodeState,
    onFilled: (code: String) -> Unit
) {
    val code = remember { mutableStateListOf<Char>() }

    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) { temp.add(FocusRequester()) }
        temp
    }

    val width = LocalConfiguration.current.screenWidthDp

    val size = when {
        width >= 16 * (length - 1) + 50 * length -> 50.dp
        else -> ((width - 16 * (length - 1)) / length).dp
    }

    val localFocusManager = LocalFocusManager.current

    Row(horizontalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.width(8.dp))

        repeat(length) { index ->
            var tfv = TextFieldValue(code.getOrNull(index = index)?.takeIf {
                it.isDigit()
            }?.toString() ?: "", TextRange(1))

            OutlinedTextField(
                modifier = Modifier
                    .width(size)
                    .height(56.dp)
                    .focusRequester(focusRequester = focusRequesters[index]),
                singleLine = true,
                value = tfv,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                onValueChange = { textFieldValue ->
                    tfv = tfv.copy(selection = TextRange(1))
                    val value: String = textFieldValue.text

                    val move = {
                        if (code.lastIndex < index) focusRequesters.getOrNull(code.lastIndex + 1)
                            ?.requestFocus()
                        else {
                            focusRequesters.getOrNull(index + 1)
                                ?.requestFocus() ?: run {
                                onFilled(code.joinToString(separator = ""))
                                localFocusManager.clearFocus()
                            }
                        }
                    }

                    if (focusRequesters[index].freeFocus()) {
                        if (value == "" && code.size > index) {
                            code.removeAt(index = index)
                            focusRequesters.getOrNull(index - 1)?.requestFocus()
                        } else {
                            if (code.size > index) {
                                value.lastOrNull().let {
                                    if (it?.isDigit() == true && value != code.getOrNull(index)
                                            ?.toString()
                                    ) {
                                        code[index] = it
                                        move()
                                    }
                                }
                            } else if (value != code.getOrNull(index)?.toString()) {
                                value.getOrNull(0).let {
                                    if (it?.isDigit() == true) {
                                        code.add(it)
                                        move()
                                    }
                                }
                            }
                        }
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(cursorColor = Color.Transparent),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}