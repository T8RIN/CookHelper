package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun RestorePasswordField(mod: Float, viewModel: AuthViewModel) {

    var login by rememberSaveable { mutableStateOf("") }
    val toastHost = LocalToastHost.current
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    var code by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordRepeat by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by derivedStateOf {
        password.isNotEmpty() && passwordRepeat == password
    }

    Text(stringResource(R.string.password_restore), style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.size(8.dp * mod))

    AnimatedContent(viewModel.restorePasswordState.value) { state ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(if (state.state == RestoreState.Login) R.string.type_your_email else R.string.type_new_password_and_code),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.size(64.dp * mod))

            if (state.isLoading) Loading(Modifier.fillMaxWidth())
            else when (state.state) {
                RestoreState.Login -> {
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = { Text(stringResource(R.string.email_or_nick)) },
                        singleLine = true,
                        isError = login.isEmpty(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (login.isNotEmpty()) {
                                viewModel.restorePasswordBy(login)
                                focus.clearFocus()
                            }
                        }),
                        modifier = Modifier.width(TextFieldDefaults.MinWidth),
                        trailingIcon = {
                            if (login.isNotBlank())
                                IconButton(onClick = { login = "" }) {
                                    Icon(Icons.Filled.Clear, null)
                                }
                        }
                    )
                }
                RestoreState.Password -> {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        singleLine = true,
                        isError = password.isEmpty() || passwordRepeat != password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.width(TextFieldDefaults.MinWidth),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(
                                    if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        }
                    )
                    Spacer(Modifier.size(8.dp * mod))
                    OutlinedTextField(
                        value = passwordRepeat,
                        onValueChange = { passwordRepeat = it },
                        label = { Text(stringResource(R.string.repeat_password)) },
                        singleLine = true,
                        isError = passwordRepeat.isEmpty() || passwordRepeat != password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.width(TextFieldDefaults.MinWidth),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(
                                    if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        }
                    )
                    AnimatedVisibility(isFormValid) {
                        Column {
                            Spacer(Modifier.size(32.dp * mod))
                            OTPField(
                                length = 5,
                                codeState = viewModel.restorePasswordCodeState.value,
                                onChange = { localCode -> code = localCode }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.size(48.dp * mod))
            Button(
                enabled = if (state.state == RestoreState.Login) {
                    if (!state.isLoading) login.isNotEmpty() else false
                } else isFormValid && code.length == 5,
                onClick = {
                    if (state.state == RestoreState.Login) viewModel.restorePasswordBy(login) else viewModel.applyPasswordByCode(
                        code,
                        password
                    )
                    focus.clearFocus()
                },
                modifier = Modifier.defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth
                ),
                content = { Text(stringResource(if (state.state == RestoreState.Login) R.string.send_email else R.string.save)) }
            )
            Spacer(Modifier.size(64.dp * mod))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(if (state.state == RestoreState.Login) R.string.have_account else R.string.wrong_credentials_question),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(Modifier.size(12.dp))
                TextButton(
                    onClick = { viewModel.apply { if (state.state == RestoreState.Login) openLogin() else openPasswordRestore() } },
                    content = { Text(stringResource(if (state.state == RestoreState.Login) R.string.log_in_have_acc else R.string.go_back)) }
                )
            }
            Spacer(Modifier.size(16.dp * mod))
        }
    }

    if (viewModel.restorePasswordState.value.found) {
        toastHost.sendToast(
            Icons.Outlined.Email,
            context.getString(R.string.check_email_to_restore)
        )
        viewModel.resetState()
    }

    if (viewModel.restorePasswordState.value.error.isNotEmpty()) {
        toastHost.sendToast(
            Icons.Outlined.ErrorOutline,
            viewModel.restorePasswordState.value.error.asString()
        )
        viewModel.resetState()
    }

}