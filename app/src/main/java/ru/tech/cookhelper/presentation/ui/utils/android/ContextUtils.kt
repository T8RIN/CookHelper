package ru.tech.cookhelper.presentation.ui.utils.android

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object ContextUtils {
    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
