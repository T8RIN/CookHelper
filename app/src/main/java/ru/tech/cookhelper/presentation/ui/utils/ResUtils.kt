package ru.tech.cookhelper.presentation.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.R

object ResUtils {

    fun Int.asString(context: Context, vararg formatArgs: Any = emptyArray()): String {
        return context.getString(this, formatArgs)
    }

    infix fun Screen.iconWith(selected: Boolean): ImageVector =
        if (selected) this.selectedIcon else this.baseIcon

    fun Screen.alternateIcon(selected: Boolean): Int {
        alternateIconsMap[this::class.name]?.apply { return if (selected) this.selectedIcon else this.defaultIcon }
        throw IllegalArgumentException("illegal screen to calculate alternate icon")
    }

    private val alternateIconsMap: Map<String, Icon> = mutableMapOf(
        Screen.Fridge::class.name to Icon(R.drawable.fridge_outline, R.drawable.fridge)
    )

    private data class Icon(val defaultIcon: Int, val selectedIcon: Int)

}