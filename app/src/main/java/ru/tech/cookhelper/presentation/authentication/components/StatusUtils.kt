package ru.tech.cookhelper.presentation.authentication.components

import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.constants.Status.ANSWER_NOT_ADDED
import ru.tech.cookhelper.core.constants.Status.COMMENT_DELETED
import ru.tech.cookhelper.core.constants.Status.COMMENT_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.EMAIL_REJECTED
import ru.tech.cookhelper.core.constants.Status.EXCEPTION
import ru.tech.cookhelper.core.constants.Status.NICKNAME_REJECTED
import ru.tech.cookhelper.core.constants.Status.PARAMETER_MISSED
import ru.tech.cookhelper.core.constants.Status.PASSWORD_REJECTED
import ru.tech.cookhelper.core.constants.Status.PERMISSION_DENIED
import ru.tech.cookhelper.core.constants.Status.POST_DELETED
import ru.tech.cookhelper.core.constants.Status.POST_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.RECIPE_DELETED
import ru.tech.cookhelper.core.constants.Status.RECIPE_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.SUCCESS
import ru.tech.cookhelper.core.constants.Status.TOKEN_EXPIRED
import ru.tech.cookhelper.core.constants.Status.TOPIC_DELETED
import ru.tech.cookhelper.core.constants.Status.TOPIC_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.USER_DELETED
import ru.tech.cookhelper.core.constants.Status.USER_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.USER_NOT_VERIFIED
import ru.tech.cookhelper.core.constants.Status.USER_PARAMETER_MISSED
import ru.tech.cookhelper.core.constants.Status.USER_UPLOAD_FAILED
import ru.tech.cookhelper.core.constants.Status.WRONG_CREDENTIALS
import ru.tech.cookhelper.core.constants.Status.WRONG_DATA

fun Int?.getMessage(): Int {
    if (this == null) return R.string.unexpected_error

    return when (this) {
        SUCCESS -> TODO()
        WRONG_DATA -> TODO()
        PERMISSION_DENIED -> TODO()
        PARAMETER_MISSED -> TODO()
        EXCEPTION -> TODO()
        USER_NOT_FOUND -> R.string.user_not_found
        WRONG_CREDENTIALS -> R.string.wrong_password_or_nick
        USER_NOT_VERIFIED -> TODO()
        USER_PARAMETER_MISSED -> TODO()
        USER_DELETED -> TODO()
        NICKNAME_REJECTED -> R.string.nickname_rejected
        EMAIL_REJECTED -> R.string.email_rejected
        PASSWORD_REJECTED -> TODO()
        TOKEN_EXPIRED -> TODO()
        USER_UPLOAD_FAILED -> TODO()
        RECIPE_NOT_FOUND -> TODO()
        RECIPE_DELETED -> TODO()
        TOPIC_NOT_FOUND -> TODO()
        TOPIC_DELETED -> TODO()
        ANSWER_NOT_ADDED -> TODO()
        COMMENT_NOT_FOUND -> TODO()
        COMMENT_DELETED -> TODO()
        POST_NOT_FOUND -> TODO()
        POST_DELETED -> TODO()
        else -> R.string.unexpected_error
    }
}