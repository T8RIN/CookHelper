package ru.tech.cookhelper.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.CookHelperApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalFoundationApi::class,
        ExperimentalAnimationApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CookHelper)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CookHelperApp(this)
        }
    }

    val isSystemBarsHidden: Boolean
        get() {
            return _isSystemBarsHidden
        }

    fun hideSystemBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).let { controller ->
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
        _isSystemBarsHidden = true
    }

    fun showSystemBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.systemBars()).also {
        _isSystemBarsHidden = false
    }

    companion object {
        private var _isSystemBarsHidden = false
    }
}