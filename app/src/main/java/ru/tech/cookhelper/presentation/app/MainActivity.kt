package ru.tech.cookhelper.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.cookhelper.presentation.app.components.CookHelperApp
import ru.tech.cookhelper.presentation.app.components.GlobalExceptionHandler
import ru.tech.cookhelper.presentation.crash_screen.CrashActivity
import ru.tech.cookhelper.presentation.ui.utils.provider.provideWindowSizeClass

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        GlobalExceptionHandler.initialize(applicationContext, CrashActivity::class.java)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            provideWindowSizeClass {
                CookHelperApp()
            }
        }
    }
}