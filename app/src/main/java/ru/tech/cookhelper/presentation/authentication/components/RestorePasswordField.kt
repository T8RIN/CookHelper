package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@Composable
fun RestorePasswordField(mod: Float, viewModel: AuthViewModel) {

    var email by rememberSaveable { mutableStateOf("") }
    val toastHost = LocalToastHost.current
    val context = LocalContext.current

    Text(stringResource(R.string.password_restore), style = MaterialTheme.typography.headlineLarge)
    Spacer(Modifier.size(8.dp * mod))
    Text(stringResource(R.string.type_your_email), style = MaterialTheme.typography.bodyLarge)
    Spacer(Modifier.size(64.dp * mod))
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(stringResource(R.string.email)) },
        singleLine = true,
        isError = !email.isValid(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
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
    Spacer(Modifier.size(48.dp * mod))
    Button(
        enabled = email.isValid(),
        onClick = {
            toastHost.sendToast(Icons.Outlined.Email, context.getString(R.string.check_email_to_restore))
            viewModel.restorePasswordBy(email)
        },
        modifier = Modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth
        ), content = { Text(stringResource(R.string.send_email)) }
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
    Spacer(Modifier.size(8.dp * mod))
}