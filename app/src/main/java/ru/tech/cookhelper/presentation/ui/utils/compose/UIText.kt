package ru.tech.cookhelper.presentation.ui.utils.compose

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    class StringResource(
        @StringRes val resId: Int, vararg val args: Any
    ) : UIText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }

    fun isEmpty(): Boolean {
        return when (this) {
            is DynamicString -> value.isEmpty()
            is StringResource -> false
        }
    }

    fun isNotEmpty(): Boolean = !isEmpty()

    @Suppress("FunctionName")
    companion object {
        fun Empty() = UIText.DynamicString("")
        fun String.asUIText() = UIText.DynamicString(this)
        fun Int.asUIText() = UIText.StringResource(this)
        fun UIText(value: String?) = UIText.DynamicString(value)
        fun UIText(@StringRes value: Int) = UIText.StringResource(value)
        fun DynamicString(value: String?) = UIText.DynamicString(value ?: "")
    }

}