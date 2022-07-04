package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Face
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Loading
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@ExperimentalAnimationApi
@Composable
fun LoginField(mod: Float, viewModel: AuthViewModel) {

    val toastHost = LocalToastHost.current
    val context = LocalContext.current

    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isFormValid by derivedStateOf {
        login.isNotEmpty() && password.isNotEmpty()
    }

    val focusManager = LocalFocusManager.current


    Text(stringResource(R.string.welcome), style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center)
    Spacer(Modifier.size(8.dp * mod))
    Text(stringResource(R.string.login_to_your_account), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    Spacer(Modifier.size(64.dp * mod))
    AnimatedContent(viewModel.loginState.value.isLoading) { isLoading ->
        Column {
            if (isLoading) Loading(Modifier.fillMaxWidth())
            else {
                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text(stringResource(R.string.email_or_nick)) },
                    singleLine = true,
                    isError = login.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    trailingIcon = {
                        if (login.isNotBlank())
                            IconButton(onClick = { login = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * mod))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    singleLine = true,
                    isError = password.isEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        if (isFormValid) viewModel.logInWith(login, password)
                    }),
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
                Row(
                    modifier = Modifier.defaultMinSize(
                        minWidth = TextFieldDefaults.MinWidth
                    ),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { viewModel.openPasswordRestore() },
                        content = { Text(stringResource(R.string.forgot), color = Color.Gray) })
                }
            }
        }
    }
    Spacer(Modifier.size(48.dp * mod))
    Button(
        enabled = if (!viewModel.loginState.value.isLoading) isFormValid else false,
        onClick = { viewModel.logInWith(login, password) },
        modifier = Modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth
        ), content = { Text(stringResource(R.string.log_in)) }
    )
    Spacer(Modifier.size(64.dp * mod))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.need_account),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { viewModel.openRegistration() },
            content = { Text(stringResource(R.string.sign_up)) }
        )
    }
    Spacer(Modifier.size(16.dp * mod))

    viewModel.loginState.value.error.let { errorMessage ->
        if (errorMessage.isNotEmpty()) {
            toastHost.sendToast(
                Icons.Outlined.ErrorOutline,
                errorMessage.asString(context)
            )
            viewModel.resetState()
        }
    }

    val user = viewModel.loginState.value.user
    if (user != null && user.verified) {
        val message = stringResource(R.string.welcome_user, user.name)
        toastHost.sendToast(Icons.Outlined.Face, message)
        viewModel.resetState()
    }

}