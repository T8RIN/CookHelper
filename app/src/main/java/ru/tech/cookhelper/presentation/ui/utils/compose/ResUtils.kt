package ru.tech.cookhelper.presentation.ui.utils.compose

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen

object ResUtils {

    fun Int.asString(context: Context, vararg formatArgs: Any = emptyArray()): String {
        return context.getString(this, formatArgs)
    }

    fun Screen.getIcon(selected: Boolean): ImageVector =
        if (selected) this.selectedIcon else this.baseIcon

    @Composable
    fun stringResourceListOf(
        @StringRes vararg ids: Int
    ): List<String> = ids.map {
        stringResource(it)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun pluralStringResource(
        @PluralsRes id: Int,
        count: Int,
        onZero: @Composable () -> String,
    ): String = if (count == 0) {
        onZero()
    } else {
        androidx.compose.ui.res.pluralStringResource(id, count, count)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun pluralStringResource(
        @PluralsRes id: Int,
        count: Int,
        onZero: @Composable () -> String,
        vararg formatArgs: Any
    ): String = if (count == 0) {
        onZero()
    } else {
        androidx.compose.ui.res.pluralStringResource(id, count, formatArgs)
    }


}