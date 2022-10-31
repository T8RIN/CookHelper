package ru.tech.cookhelper.presentation.login_screen

import androidx.compose.animation.AnimatedContent
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
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.login_screen.viewModel.LoginViewModel
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun LoginField(
    scaleModifier: Float,
    onGetCredentials: (name: String?, email: String?, token: String?) -> Unit,
    authController: NavController<Screen>,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val toastHost = LocalToastHost.current
    val context = LocalContext.current

    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isFormValid by remember {
        derivedStateOf {
            login.isNotEmpty() && password.isNotEmpty()
        }
    }

    val focusManager = LocalFocusManager.current


    Text(
        stringResource(R.string.welcome),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(8.dp * scaleModifier))
    Text(
        stringResource(R.string.login_to_your_account),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(64.dp * scaleModifier))
    AnimatedContent(viewModel.loginState) { state ->
        Column {
            if (state.isLoading) Loading(Modifier.fillMaxWidth())
            else {
                CozyTextField(
                    value = login,
                    appearance = TextFieldAppearance.Outlined,
                    onValueChange = { login = it },
                    label = { Text(stringResource(R.string.email_or_nick)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(TextFieldDefaults.MinWidth),
                    endIcon = {
                        if (login.isNotBlank())
                            IconButton(onClick = { login = "" }) {
                                Icon(Icons.Filled.Clear, null)
                            }
                    }
                )
                Spacer(Modifier.size(8.dp * scaleModifier))
                CozyTextField(
                    value = password,
                    appearance = TextFieldAppearance.Outlined,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    singleLine = true,
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
                Row(
                    modifier = Modifier.defaultMinSize(
                        minWidth = TextFieldDefaults.MinWidth
                    ),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { authController.navigate(Screen.Authentication.RestorePassword) },
                        content = { Text(stringResource(R.string.forgot), color = Gray) })
                }
            }
        }
    }
    Spacer(Modifier.size(48.dp * scaleModifier))
    Button(
        enabled = if (!viewModel.loginState.isLoading) isFormValid else false,
        onClick = { viewModel.logInWith(login, password) },
        modifier = Modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth
        ), content = { Text(stringResource(R.string.log_in)) }
    )
    Spacer(Modifier.size(64.dp * scaleModifier))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.need_account),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { authController.navigate(Screen.Authentication.Register) },
            content = { Text(stringResource(R.string.sign_up)) }
        )
    }
    Spacer(Modifier.size(16.dp * scaleModifier))

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                it.icon,
                it.text.asString(context)
            )
            is Event.SendData -> {
                onGetCredentials(it["name"], it["email"], it["token"])
            }
            is Event.NavigateTo -> {
                authController.navigate(it.screen)
            }
            else -> {}
        }
    }
}