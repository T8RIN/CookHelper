package ru.tech.cookhelper.presentation.ui.utils

import android.app.Activity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object StatusBarUtils {

    val Activity.isSystemBarsHidden: Boolean
        get() {
            return _isSystemBarsHidden
        }

    private var _isSystemBarsHidden = false

    val Activity.isNavigationBarsHidden: Boolean
        get() {
            return _isNavigationBarsHidden
        }

    private var _isNavigationBarsHidden = false

    fun Activity.hideSystemBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).let { controller ->
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
        _isSystemBarsHidden = true
    }

    fun Activity.hideNavigationBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).let { controller ->
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        _isNavigationBarsHidden = true
    }

    fun Activity.showNavigationBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.navigationBars()).also {
        _isNavigationBarsHidden = false
    }

    fun Activity.showSystemBars() = WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.systemBars()).also {
        _isSystemBarsHidden = false
    }
}