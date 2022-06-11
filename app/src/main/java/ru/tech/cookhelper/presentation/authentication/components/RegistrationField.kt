package ru.tech.cookhelper.presentation.authentication.components

import android.util.Patterns
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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

@ExperimentalAnimationApi
@Composable
fun RegistrationField(mod: Float, viewModel: AuthViewModel) {

    val toastHost = LocalToastHost.current
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
    val isFormValid by derivedStateOf {
        name.isNotEmpty() && surname.isNotEmpty() && nick.isNotEmpty() && password.isNotEmpty() && email.isValid() && passwordRepeat == password
    }

    val focusManager = LocalFocusManager.current

    Text(stringResource(R.string.register), style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.size(8.dp * mod))
    Text(
        stringResource(R.string.create_your_new_account),
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(Modifier.size(32.dp * mod))
    AnimatedContent(viewModel.registrationState.value.isLoading) { isLoading ->
        Column {
            if (isLoading) Loading(Modifier.fillMaxWidth())
            else {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    isError = name.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    trailingIcon = {
                        if (name.isNotBlank())
                            IconButton(onClick = { name = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * mod))
                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text(stringResource(R.string.surname)) },
                    singleLine = true,
                    isError = surname.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    trailingIcon = {
                        if (surname.isNotBlank())
                            IconButton(onClick = { surname = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * mod))
                OutlinedTextField(
                    value = nick,
                    onValueChange = { nick = it },
                    label = { Text(stringResource(R.string.nick)) },
                    singleLine = true,
                    isError = nick.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    trailingIcon = {
                        if (nick.isNotBlank())
                            IconButton(onClick = { nick = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * mod))
                AnimatedVisibility(
                    visible = name.isNotEmpty() && surname.isNotEmpty() && nick.isNotEmpty(),
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Column {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(stringResource(R.string.email)) },
                            singleLine = true,
                            isError = email.isEmpty() || email.isNotValid(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.width(TextFieldDefaults.MinWidth),
                            trailingIcon = {
                                if (email.isNotBlank())
                                    IconButton(onClick = { email = "" }) {
                                        Icon(Icons.Filled.Clear, null)
                                    }
                            }
                        )
                        Spacer(Modifier.size(8.dp))
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
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.width(TextFieldDefaults.MinWidth),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                                if (isFormValid) viewModel.registerWith(
                                    name.capitalize(),
                                    surname.capitalize(),
                                    nick, email, password
                                )
                            }),
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
                    }
                }
            }
        }
    }

    Spacer(Modifier.size(32.dp * mod))
    Button(
        enabled = if (!viewModel.registrationState.value.isLoading) isFormValid else false,
        onClick = {
            viewModel.registerWith(
                name.capitalize(),
                surname.capitalize(),
                nick, email, password
            )
        },
        modifier = Modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth
        ), content = { Text(stringResource(R.string.sign_up)) }
    )
    Spacer(Modifier.size(64.dp * mod))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.have_account),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { viewModel.openLogin() },
            content = { Text(stringResource(R.string.log_in_have_acc)) })
    }
    Spacer(Modifier.size(16.dp * mod))

    viewModel.registrationState.value.error.let { errorMessage ->
        if (errorMessage.isNotEmpty()) {
            toastHost.sendToast(
                Icons.Outlined.ErrorOutline,
                errorMessage.asString(context)
            )
            viewModel.resetState()
        }
    }
}

fun String.capitalize() = lowercase().replaceFirstChar { it.titlecase() }
fun String.isValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isNotValid() = !isValid()