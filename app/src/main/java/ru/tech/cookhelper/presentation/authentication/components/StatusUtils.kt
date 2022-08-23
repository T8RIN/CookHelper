package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.R

fun Int?.getMessage(): Int {
    if (this == null) return R.string.unexpected_error

    return when (this) {
        101 -> R.string.user_not_found
        102 -> R.string.wrong_password_or_nick
        103 -> TODO()
        104 -> TODO()
        105 -> TODO()
        106 -> TODO()
        107 -> TODO()
        108 -> TODO()
        109 -> TODO()
        else -> R.string.unexpected_error
    }
}
