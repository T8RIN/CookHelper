package ru.tech.cookhelper.presentation.registration_screen

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
import ru.tech.cookhelper.presentation.registration_screen.viewModel.RegistrationViewModel
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun RegistrationField(
    scaleModifier: Float,
    authController: NavController<Screen>,
    onTitleChange: (UIText) -> Unit,
    onGetCredentials: (name: String?, email: String?, token: String?) -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val screenController = LocalScreenController.current
    val toastHost = LocalToastHostState.current
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var nick by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordRepeat by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by remember {
        derivedStateOf {
            name.isNotEmpty() && surname.isNotEmpty() && nick.isNotEmpty() && password.isNotEmpty() && viewModel.emailState.isValid && passwordRepeat == password && viewModel.loginState.isValid && viewModel.isPasswordValid
        }
    }

    val focusManager = LocalFocusManager.current

    BackHandler { authController.goBack() }

    Text(
        stringResource(R.string.register),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(8.dp * scaleModifier))
    Text(
        stringResource(R.string.create_your_new_account),
        style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(32.dp * scaleModifier))
    AnimatedContent(viewModel.registrationState.isLoading) { isLoading ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) Loading(Modifier.fillMaxWidth())
            else {
                CozyTextField(
                    value = name,
                    appearance = TextFieldAppearance.Outlined,
                    onValueChange = { name = it.trim() },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    isError = name.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        Text(stringResource(R.string.required_field))
                    },
                    supportingTextVisible = name.isEmpty(),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    endIcon = {
                        if (name.isNotBlank())
                            IconButton(onClick = { name = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * scaleModifier))
                CozyTextField(
                    value = surname,
                    appearance = TextFieldAppearance.Outlined,
                    onValueChange = { surname = it.trim() },
                    label = { Text(stringResource(R.string.surname)) },
                    singleLine = true,
                    isError = surname.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        Text(stringResource(R.string.required_field))
                    },
                    supportingTextVisible = surname.isEmpty(),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    endIcon = {
                        if (surname.isNotBlank())
                            IconButton(onClick = { surname = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * scaleModifier))
                CozyTextField(
                    value = nick,
                    appearance = TextFieldAppearance.Outlined,
                    loading = viewModel.loginState.isLoading,
                    onValueChange = {
                        nick = it
                        viewModel.validateLogin(it)
                    },
                    label = { Text(stringResource(R.string.nick)) },
                    singleLine = true,
                    isError = !viewModel.loginState.isValid,
                    supportingText = {
                        Text(viewModel.loginState.error.asString())
                    },
                    supportingTextVisible = !viewModel.loginState.isValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    endIcon = {
                        if (nick.isNotBlank()) {
                            IconButton(onClick = { nick = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                        }
                    }
                )
                AnimatedVisibility(name.isNotEmpty() && surname.isNotEmpty() && nick.isNotEmpty()) {
                    Column {
                        Spacer(Modifier.size(8.dp * scaleModifier))
                        CozyTextField(
                            value = email,
                            appearance = TextFieldAppearance.Outlined,
                            loading = viewModel.emailState.isLoading,
                            onValueChange = {
                                email = it
                                viewModel.validateEmail(it)
                            },
                            label = { Text(stringResource(R.string.email)) },
                            singleLine = true,
                            isError = !viewModel.emailState.isValid,
                            supportingText = {
                                Text(viewModel.emailState.error.asString())
                            },
                            supportingTextVisible = !viewModel.emailState.isValid,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.width(TextFieldDefaults.MinWidth),
                            endIcon = {
                                if (email.isNotBlank()) {
                                    IconButton(onClick = { email = "" }) {
                                        Icon(Icons.Filled.Clear, null)
                                    }
                                }
                            }
                        )
                        Spacer(Modifier.size(8.dp))
                        CozyTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                viewModel.validatePassword(it)
                            },
                            label = { Text(stringResource(R.string.password)) },
                            singleLine = true,
                            isError = !viewModel.isPasswordValid,
                            supportingText = {
                                Text(viewModel.passwordValidationError.asString())
                            },
                            supportingTextVisible = !viewModel.isPasswordValid,
                            appearance = TextFieldAppearance.Outlined,
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
                                imeAction = ImeAction.Done
                            ),
                            supportingText = {
                                if (passwordRepeat.isEmpty()) Text(stringResource(R.string.required_field))
                                else Text(stringResource(R.string.passwords_dont_match))
                            },
                            supportingTextVisible = passwordRepeat.isEmpty() || passwordRepeat != password,
                            modifier = Modifier.width(TextFieldDefaults.MinWidth),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                                if (isFormValid) viewModel.registerWith(
                                    name, surname, nick,
                                    email, password
                                )
                            }),
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
                    }
                }
            }
        }
    }

    Spacer(Modifier.size(32.dp * scaleModifier))
    Button(
        enabled = if (!viewModel.registrationState.isLoading) isFormValid else false,
        onClick = {
            viewModel.registerWith(
                name, surname, nick,
                email, password
            )
        },
        modifier = Modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth
        ), content = { Text(stringResource(R.string.sign_up)) }
    )
    Spacer(Modifier.size(64.dp * scaleModifier))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.have_account),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { authController.navigate(Screen.Authentication.Login) },
            content = { Text(stringResource(R.string.log_in_have_acc)) }
        )
    }
    Spacer(Modifier.size(16.dp * scaleModifier))

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.show(
                it.icon,
                it.text.asString(context)
            )
            is Event.SendData -> {
                onGetCredentials(it["name"], it["email"], it["token"])
            }
            is Event.NavigateTo -> {
                authController.navigate(it.screen)
            }
            is Event.NavigateIf -> {
                if (it.predicate(screenController.currentDestination)) {
                    screenController.navigate(it.screen)
                    onTitleChange(Screen.Home.Feed.title)
                }
            }
            else -> {}
        }
    }
}