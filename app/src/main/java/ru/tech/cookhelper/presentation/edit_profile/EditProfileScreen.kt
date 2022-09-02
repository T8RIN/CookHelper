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
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.edit_profile.components.EditProfileItem
import ru.tech.cookhelper.presentation.edit_profile.viewModel.EditProfileViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.RoundedTextField
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel = hiltViewModel(), onBack: () -> Unit) {
    val showPassword = remember { mutableStateOf(false) }
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
                RoundedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.FormatColorText,
                    onValueChange = { viewModel.setTemp(name = it) },
                    label = stringResource(R.string.name),
                    value = viewModel.nameAndSurname.first
                )
                Spacer(Modifier.height(4.dp))
                RoundedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.ShortText,
                    onValueChange = { viewModel.setTemp(surname = it) },
                    label = stringResource(R.string.surname),
                    value = viewModel.nameAndSurname.second
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.savePersonalInfo() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(stringResource(R.string.save))
                }
            }
            Separator()
            EditProfileItem(text = stringResource(R.string.account_info)) {
                var password by rememberSaveable { mutableStateOf("") }
                RoundedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.AlternateEmail,
                    onValueChange = { viewModel.setTemp(nickname = it) },
                    label = stringResource(R.string.nick),
                    value = viewModel.nickAndEmail.first
                )
                Spacer(Modifier.height(4.dp))
                RoundedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    startIcon = Icons.Outlined.Email,
                    onValueChange = { viewModel.setTemp(email = it) },
                    label = stringResource(R.string.email),
                    value = viewModel.nickAndEmail.second
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
                ) {
                    Text(stringResource(R.string.save))
                }
            }
            Separator()
            EditProfileItem(text = stringResource(R.string.password)) {
                var password by rememberSaveable { mutableStateOf("") }
                var repeatPassword by rememberSaveable { mutableStateOf("") }
                var oldPassword by rememberSaveable { mutableStateOf("") }
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
    showPassword: MutableState<Boolean>,
    onValueChange: (String) -> Unit
) {
    RoundedTextField(
        modifier = modifier,
        startIcon = Icons.Outlined.Password,
        onValueChange = onValueChange,
        visualTransformation = if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
        label = label,
        endIcon = {
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    if (!showPassword.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    null
                )
            }
        },
        value = value
    )
}