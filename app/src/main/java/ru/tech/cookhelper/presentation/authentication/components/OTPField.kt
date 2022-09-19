package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
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
import kotlinx.coroutines.delay
import ru.tech.cookhelper.presentation.confirm_email.components.CodeState
import ru.tech.cookhelper.presentation.ui.utils.compose.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun OTPField(
    length: Int,
    codeState: CodeState,
    onFilled: (code: String) -> Unit = {},
    onChange: (code: String) -> Unit = {}
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


    var otpPos by remember { mutableStateOf(OtpPos.Center) }
    val transition = updateTransition(targetState = otpPos, label = "shake")
    val offset by transition.animateOffset(label = "shake") { state ->
        when (state) {
            OtpPos.Left -> Offset(-10F, 0F)
            OtpPos.Center -> Offset(0F, 0F)
            OtpPos.Right -> Offset(10F, 0F)
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.offset(offset.x.dp, offset.y.dp)
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        repeat(length) { index ->
            var fieldValue = TextFieldValue(code.getOrNull(index = index)?.takeIf {
                it.isDigit()
            }?.toString() ?: "", TextRange(1))

            OutlinedTextField(
                modifier = Modifier
                    .width(size)
                    .height(56.dp)
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onKeyEvent { event: KeyEvent ->
                        if (event.type == KeyEventType.KeyUp && event.key == Key.Backspace && fieldValue.text.isEmpty()) {
                            focusRequesters
                                .getOrNull(index - 1)
                                ?.requestFocus()
                                ?: focusRequesters
                                    .lastOrNull()
                                    ?.requestFocus()
                            return@onKeyEvent true
                        }
                        false
                    }
                    .shimmer(codeState.isLoading),
                singleLine = true,
                readOnly = codeState.isLoading,
                value = fieldValue,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                onValueChange = { textFieldValue ->
                    fieldValue = fieldValue.copy(selection = TextRange(1))
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
                        onChange(code.joinToString(""))
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(cursorColor = Color.Transparent),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }

    if (codeState.error) {
        LaunchedEffect(codeState) {
            onChange("")

            otpPos = OtpPos.Right
            delay(75)
            otpPos = OtpPos.Left
            delay(75)
            otpPos = OtpPos.Right
            delay(75)
            otpPos = OtpPos.Left
            delay(75)
            otpPos = OtpPos.Center

            code.asReversed().forEachIndexed { index, _ ->
                code[index] = ' '
                delay(100)
            }
            code.clear()
        }
    }

}

private enum class OtpPos {
    Left, Center, Right
}