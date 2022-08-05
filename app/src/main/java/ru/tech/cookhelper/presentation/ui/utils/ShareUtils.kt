package ru.tech.cookhelper.presentation.ui.utils

import android.content.Context
import android.content.Intent
import ru.tech.cookhelper.R

object ShareUtils {

    fun Context.shareWith(value: String?) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, value)
            type = "text/plain"
        }
        startActivity(
            Intent.createChooser(
                intent,
                getString(R.string.share)
            )
        )
    }

}