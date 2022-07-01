package ru.tech.cookhelper.presentation.authentication

import android.content.pm.ActivityInfo
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.LockScreenOrientation
import ru.tech.cookhelper.presentation.authentication.components.*
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun AuthenticationScreen(viewModel: AuthViewModel = scopedViewModel()) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val height = LocalConfiguration.current.screenHeightDp

    val mod: Float = when {
        height >= 850 -> 1.5f
        height >= 650 -> 1f
        height >= 450 -> 0.5f
        else -> 0.1f
    }

    val localFocusManager = LocalFocusManager.current

    LazyColumn(
        contentPadding = WindowInsets.systemBars.asPaddingValues(),
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { localFocusManager.clearFocus() }
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(16.dp * mod))
                Image(
                    painterResource(id = R.drawable.ic_logo_144),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(Modifier.size(20.dp * mod))
                AnimatedContent(
                    targetState = viewModel.authState.value,
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = {
                        fadeIn(animationSpec = tween(350, delayMillis = 150)) with fadeOut(
                            animationSpec = tween(150)
                        )
                    }
                ) { authState ->
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (authState) {
                            AuthState.Login -> LoginField(mod = mod, viewModel = viewModel)
                            AuthState.Registration -> RegistrationField(mod = mod, viewModel = viewModel)
                            AuthState.RestorePassword -> RestorePasswordField(mod = mod, viewModel = viewModel)
                            AuthState.ConfirmEmail -> ConfirmEmailField(mod = mod, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}