package ru.tech.prokitchen.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import ru.tech.prokitchen.R
import ru.tech.prokitchen.presentation.app.components.ProKitchenApp

@ExperimentalSnapperApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProKitchen)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProKitchenApp(this)
        }
    }
}