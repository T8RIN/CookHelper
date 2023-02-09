package ru.tech.cookhelper.presentation.edit_profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.edit_profile.components.EditProfileItem
import ru.tech.cookhelper.presentation.edit_profile.viewModel.EditProfileViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.widgets.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }
    val surname = viewModel.nameAndSurname.second
    val name = viewModel.nameAndSurname.first
    val email = viewModel.nickAndEmail.second
    val nickname = viewModel.nickAndEmail.first

    Column(
        Modifier
            .navigationBarsPadding()
            .imePadding()
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.edit)) },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            },
            background = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
        Column(Modifier.verticalScroll(rememberScrollState())) {
            EditProfileItem(text = stringResource(R.string.personal_info)) {
                CozyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.FormatColorText,
                    onValueChange = { viewModel.setTemp(name = it.trim()) },
                    label = stringResource(R.string.name),
                    value = name
                )
                Spacer(Modifier.height(4.dp))
                CozyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.ShortText,
                    onValueChange = { viewModel.setTemp(surname = it.trim()) },
                    label = stringResource(R.string.surname),
                    value = surname
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.savePersonalInfo() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = name.isNotEmpty() && surname.isNotEmpty()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
            Separator()
            EditProfileItem(text = stringResource(R.string.account_info)) {
                var password by rememberSaveable { mutableStateOf("") }
                CozyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    loading = viewModel.loginState.isLoading,
                    isError = !viewModel.loginState.isValid,
                    supportingText = {
                        Text(viewModel.loginState.error.asString())
                    },
                    supportingTextVisible = !viewModel.loginState.isValid,
                    startIcon = Icons.Outlined.AlternateEmail,
                    onValueChange = {
                        viewModel.setTemp(nickname = it)
                        viewModel.validateLogin(it)
                    },
                    singleLine = true,
                    label = stringResource(R.string.nick),
                    value = nickname
                )
                Spacer(Modifier.height(4.dp))
                CozyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    loading = viewModel.emailState.isLoading,
                    isError = !viewModel.emailState.isValid,
                    supportingText = { isError ->
                        Text(viewModel.emailState.error.asString())
                        //TODO: cock
                    },
                    singleLine = true,
                    startIcon = Icons.Outlined.Email,
                    onValueChange = {
                        viewModel.setTemp(email = it)
                        viewModel.validateEmail(it)
                    },
                    label = stringResource(R.string.email),
                    value = email
                )
                Spacer(Modifier.height(8.dp))
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { password = it },
                    label = stringResource(R.string.current_password),
                    value = password,
                    showPassword = showPassword
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.saveAccountInfo(password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = password.isNotEmpty() && viewModel.emailState.isValid
                            && viewModel.loginState.error.isEmpty()
                            && !viewModel.emailState.isLoading
                            && !viewModel.loginState.isLoading
                            && nickname.isNotEmpty()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
            Separator()
            EditProfileItem(text = stringResource(R.string.password)) {
                var password by rememberSaveable { mutableStateOf("") }
                var repeatPassword by rememberSaveable { mutableStateOf("") }
                var oldPassword by rememberSaveable { mutableStateOf("") }
                val canSaveNewPassword by remember {
                    derivedStateOf {
                        password == repeatPassword && oldPassword.isNotEmpty()
                    }
                }
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { password = it },
                    label = stringResource(R.string.new_password),
                    value = password,
                    showPassword = showPassword
                )
                Spacer(Modifier.height(4.dp))
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { repeatPassword = it },
                    label = stringResource(R.string.repeat_password),
                    isError = password != repeatPassword,
                    error = {
                        Text(stringResource(R.string.passwords_dont_match))
                    },
                    value = repeatPassword,
                    showPassword = showPassword
                )
                Spacer(Modifier.height(8.dp))
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onValueChange = { oldPassword = it },
                    label = stringResource(R.string.current_password),
                    value = oldPassword,
                    showPassword = showPassword
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.savePassword(password, oldPassword) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = canSaveNewPassword
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
    BackHandler { onBack() }

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            /*TODO: Proper handling*/
            else -> {}
        }
    }
}

@Composable
private fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    isError: Boolean = false,
    error: (@Composable (Boolean) -> Unit)? = null,
    showPassword: MutableState<Boolean>,
    onValueChange: (String) -> Unit
) {
    CozyTextField(
        modifier = modifier,
        startIcon = {
            Icon(Icons.Outlined.Password, null)
        },
        onValueChange = onValueChange,
        visualTransformation = if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
        label = { Text(label) },
        endIcon = {
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    if (!showPassword.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    null
                )
            }
        },
        supportingText = error,
        supportingTextVisible = isError,
        isError = isError,
        value = value
    )
}