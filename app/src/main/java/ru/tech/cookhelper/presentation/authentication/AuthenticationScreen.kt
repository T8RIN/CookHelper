package ru.tech.cookhelper.presentation.authentication

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.app.components.LockScreenOrientation

@ExperimentalMaterial3Api
@Composable
fun AuthenticationScreen() {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.size(8.dp))
            Text("Login to your account", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.size(64.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                singleLine = true
            )
            Spacer(Modifier.size(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                singleLine = true
            )
            Spacer(Modifier.size(48.dp))
            Button(onClick = {}, modifier = Modifier
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth
                ), content = { Text("Log in") })
            Spacer(Modifier.size(64.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Create account?", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.size(12.dp))
                TextButton(onClick = {}, content = { Text("Sign up") })
            }
        }
    }
}