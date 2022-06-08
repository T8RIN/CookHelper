package ru.tech.cookhelper.presentation.authentication.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel

@Composable
fun LoginField(mod: Float, viewModel: AuthViewModel) {

    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isFormValid by derivedStateOf {
        login.isNotEmpty() && password.isNotEmpty()
    }

    val focusManager = LocalFocusManager.current


    Text(stringResource(R.string.welcome), style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.size(8.dp * mod))
    Text(stringResource(R.string.login_to_your_account), style = MaterialTheme.typography.bodyLarge)
    Spacer(Modifier.size(64.dp * mod))
    OutlinedTextField(
        value = login,
        onValueChange = { login = it },
        label = { Text(stringResource(R.string.email_or_nick)) },
        singleLine = true,
        isError = login.isEmpty(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
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
            onClick = { viewModel.forgotPassword() },
            content = { Text(stringResource(R.string.forgot), color = Color.Gray) })
    }
    Spacer(Modifier.size(48.dp * mod))
    Button(
        enabled = isFormValid,
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
            onClick = { viewModel.signUp() },
            content = { Text(stringResource(R.string.sign_up)) })
    }
    Spacer(Modifier.size(8.dp * mod))
}