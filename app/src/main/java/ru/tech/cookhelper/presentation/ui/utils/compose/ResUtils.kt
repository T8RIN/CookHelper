package ru.tech.cookhelper.presentation.ui.utils.compose

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

object ResUtils {

    fun Int.asString(context: Context, vararg formatArgs: Any = emptyArray()): String {
        return context.getString(this, formatArgs)
    }

    infix fun Screen.iconWith(selected: Boolean): ImageVector =
        if (selected) this.selectedIcon else this.baseIcon

}