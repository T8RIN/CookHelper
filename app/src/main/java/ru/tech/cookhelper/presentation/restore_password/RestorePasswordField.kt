package ru.tech.cookhelper.presentation.restore_password

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.CozyTextField
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.TextFieldAppearance
import ru.tech.cookhelper.presentation.authentication.components.OTPField
import ru.tech.cookhelper.presentation.restore_password.components.RestoreState
import ru.tech.cookhelper.presentation.restore_password.viewModel.RestorePasswordViewModel
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.provider.goBack
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun RestorePasswordField(
    scaleModifier: Float,
    authController: NavController<Screen>,
    viewModel: RestorePasswordViewModel = hiltViewModel()
) {

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

    val isFormValid by remember {
        derivedStateOf {
            password.isNotEmpty() && passwordRepeat == password
        }
    }

    Text(
        stringResource(R.string.password_restore),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(8.dp * scaleModifier))

    AnimatedContent(viewModel.restorePasswordState) { state ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(if (state.state == RestoreState.Login) R.string.type_your_email else R.string.type_new_password_and_code),
                style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(64.dp * scaleModifier))

            if (state.isLoading) Loading(Modifier.fillMaxWidth())
            else when (state.state) {
                RestoreState.Login -> {
                    BackHandler { authController.goBack() }

                    CozyTextField(
                        value = login,
                        appearance = TextFieldAppearance.Outlined,
                        onValueChange = { login = it },
                        label = { Text(stringResource(R.string.email_or_nick)) },
                        singleLine = true,
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
                        endIcon = {
                            if (login.isNotBlank())
                                IconButton(onClick = { login = "" }) {
                                    Icon(Icons.Filled.Clear, null)
                                }
                        }
                    )
                }
                RestoreState.Password -> {
                    BackHandler { viewModel.goBackPasswordRestore() }

                    CozyTextField(
                        value = password,
                        appearance = TextFieldAppearance.Outlined,
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
                        endIcon = {
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
                    Spacer(Modifier.size(8.dp * scaleModifier))
                    CozyTextField(
                        value = passwordRepeat,
                        appearance = TextFieldAppearance.Outlined,
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
                        endIcon = {
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
                            Spacer(Modifier.size(32.dp * scaleModifier))
                            OTPField(
                                length = 5,
                                codeState = viewModel.restorePasswordCodeState,
                                onChange = { localCode -> code = localCode }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.size(48.dp * scaleModifier))
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
            Spacer(Modifier.size(64.dp * scaleModifier))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(if (state.state == RestoreState.Login) R.string.have_account else R.string.wrong_credentials_question),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray
                )
                Spacer(Modifier.size(12.dp))
                TextButton(
                    onClick = {
                        if (state.state == RestoreState.Login) authController.navigate(
                            Screen.Authentication.Login
                        ) else authController.navigate(Screen.Authentication.RestorePassword)
                    },
                    content = { Text(stringResource(if (state.state == RestoreState.Login) R.string.log_in_have_acc else R.string.go_back)) }
                )
            }
            Spacer(Modifier.size(16.dp * scaleModifier))
        }
    }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.show(
                it.icon,
                it.text.asString(context)
            )
            is Event.NavigateTo -> authController.navigate(it.screen)
            else -> {}
        }
    }

}