package ru.tech.cookhelper.presentation.crash_screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material.icons.twotone.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.MainActivity
import ru.tech.cookhelper.presentation.app.components.GlobalExceptionHandler.Companion.getExceptionString
import ru.tech.cookhelper.presentation.crash_screen.viewModel.CrashViewModel
import ru.tech.cookhelper.presentation.ui.theme.CookHelperTheme
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSettingsProvider

@AndroidEntryPoint
class CrashActivity : ComponentActivity() {

    val viewModel: CrashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val crashReason = getExceptionString()

        setContent {
            CompositionLocalProvider(
                LocalSettingsProvider provides viewModel.settingsState
            ) {
                CookHelperTheme {
                    val conf = LocalConfiguration.current
                    val size = min(conf.screenWidthDp.dp, conf.screenHeightDp.dp)
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Box {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Icon(
                                    imageVector = Icons.TwoTone.ErrorOutline,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(size * 0.3f)
                                        .statusBarsPadding()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.something_went_wrong),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 26.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    shape = SquircleShape(24.dp)
                                ) {
                                    Text(
                                        text = crashReason,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .navigationBarsPadding()
                                )
                            }
                            Button(
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@CrashActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .navigationBarsPadding()
                                    .align(Alignment.BottomCenter)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.RestartAlt,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = stringResource(R.string.restart_app))
                            }
                        }
                    }
                }
            }
        }
    }

}