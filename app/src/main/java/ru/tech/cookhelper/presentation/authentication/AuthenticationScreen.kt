package ru.tech.cookhelper.presentation.authentication

import android.content.pm.ActivityInfo
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.rememberNavController
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.authentication.components.LockScreenOrientation
import ru.tech.cookhelper.presentation.confirm_email.ConfirmEmailField
import ru.tech.cookhelper.presentation.login_screen.LoginField
import ru.tech.cookhelper.presentation.registration_screen.RegistrationField
import ru.tech.cookhelper.presentation.restore_password.RestorePasswordField
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.currentDestination


@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthenticationScreen(onTitleChange: (UIText) -> Unit) {

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val authController = rememberNavController<Screen>(Screen.Authentication.Login)

    val height = LocalConfiguration.current.screenHeightDp

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var token by rememberSaveable { mutableStateOf("") }

    val scaleModifier: Float = when {
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
            }
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(16.dp * scaleModifier))
                Picture(
                    model = R.drawable.ic_launcher_foreground,
                    modifier = Modifier
                        .size(100.dp)
                        .scale(2f),
                    shimmerEnabled = false
                )
                Spacer(Modifier.size(20.dp * scaleModifier))
                AnimatedContent(
                    targetState = authController.currentDestination ?: Screen.Authentication.Login,
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = {
                        fadeIn(animationSpec = tween(350, delayMillis = 150)) with fadeOut(
                            animationSpec = tween(150)
                        )
                    }
                ) { screen ->
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (screen) {
                            Screen.Authentication.Confirmation -> {
                                ConfirmEmailField(
                                    authController = authController,
                                    scaleModifier = scaleModifier,
                                    name = name,
                                    email = email,
                                    token = token,
                                    onTitleChange = onTitleChange
                                )
                            }
                            Screen.Authentication.Login -> {
                                LoginField(
                                    scaleModifier = scaleModifier,
                                    onGetCredentials = { n, e, t ->
                                        name = n ?: ""
                                        email = e ?: ""
                                        token = t ?: ""
                                    },
                                    authController = authController,
                                    onTitleChange = onTitleChange
                                )
                            }
                            Screen.Authentication.Register -> {
                                RegistrationField(
                                    scaleModifier = scaleModifier,
                                    authController = authController,
                                    onGetCredentials = { n, e, t ->
                                        name = n ?: ""
                                        email = e ?: ""
                                        token = t ?: ""
                                    },
                                    onTitleChange = onTitleChange
                                )
                            }
                            Screen.Authentication.RestorePassword -> {
                                RestorePasswordField(
                                    scaleModifier = scaleModifier,
                                    authController = authController
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}