package ru.tech.cookhelper.presentation.m3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import ru.tech.cookhelper.presentation.crash_screen.CrashActivity
import ru.tech.cookhelper.presentation.ui.utils.android.exception.GlobalExceptionHandler

abstract class M3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        GlobalExceptionHandler.initialize(applicationContext, CrashActivity::class.java)
    }

}