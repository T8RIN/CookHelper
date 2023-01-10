package ru.tech.cookhelper.presentation.app

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.cookhelper.presentation.app.components.CookHelperApp
import ru.tech.cookhelper.presentation.m3.M3Activity
import ru.tech.cookhelper.presentation.ui.utils.provider.setContentWithWindowSizeClass

@AndroidEntryPoint
class MainActivity : M3Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithWindowSizeClass { CookHelperApp() }
    }

}