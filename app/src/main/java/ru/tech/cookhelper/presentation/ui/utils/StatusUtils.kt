package ru.tech.cookhelper.presentation.ui.utils

import androidx.annotation.StringRes
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.core.constants.Status.ANSWER_NOT_ADDED
import ru.tech.cookhelper.core.constants.Status.CHAT_DELETED
import ru.tech.cookhelper.core.constants.Status.CHAT_NOT_CREATED
import ru.tech.cookhelper.core.constants.Status.CHAT_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.COMMENT_DELETED
import ru.tech.cookhelper.core.constants.Status.COMMENT_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.CONNECTION_TIMED_OUT
import ru.tech.cookhelper.core.constants.Status.EMAIL_REJECTED
import ru.tech.cookhelper.core.constants.Status.EXCEPTION
import ru.tech.cookhelper.core.constants.Status.NICKNAME_REJECTED
import ru.tech.cookhelper.core.constants.Status.NO_INTERNET
import ru.tech.cookhelper.core.constants.Status.PARAMETER_MISSED
import ru.tech.cookhelper.core.constants.Status.PASSWORD_REJECTED
import ru.tech.cookhelper.core.constants.Status.PERMISSION_DENIED
import ru.tech.cookhelper.core.constants.Status.POST_DELETED
import ru.tech.cookhelper.core.constants.Status.POST_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.READ_TIMEOUT
import ru.tech.cookhelper.core.constants.Status.RECIPE_DELETED
import ru.tech.cookhelper.core.constants.Status.RECIPE_NOT_CREATED
import ru.tech.cookhelper.core.constants.Status.RECIPE_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.SUCCESS
import ru.tech.cookhelper.core.constants.Status.TOKEN_EXPIRED
import ru.tech.cookhelper.core.constants.Status.TOPIC_DELETED
import ru.tech.cookhelper.core.constants.Status.TOPIC_NOT_CREATED
import ru.tech.cookhelper.core.constants.Status.TOPIC_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.USER_DELETED
import ru.tech.cookhelper.core.constants.Status.USER_NOT_FOUND
import ru.tech.cookhelper.core.constants.Status.USER_NOT_VERIFIED
import ru.tech.cookhelper.core.constants.Status.USER_TOKEN_INVALID
import ru.tech.cookhelper.core.constants.Status.USER_UPLOAD_FAILED
import ru.tech.cookhelper.core.constants.Status.WRONG_CREDENTIALS
import ru.tech.cookhelper.core.constants.Status.WRONG_DATA
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText.Companion.UIText
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Int?.getUIText(): UIText = UIText(getStatusString())

@StringRes
fun Int?.getStatusString(): Int = when (this) {
    SUCCESS -> R.string.success
    NO_INTERNET -> R.string.no_connection
    CONNECTION_TIMED_OUT -> R.string.connection_timed_out
    READ_TIMEOUT -> R.string.read_timeout
    WRONG_DATA -> R.string.wrong_data
    PERMISSION_DENIED -> R.string.permission_denied
    PARAMETER_MISSED -> R.string.malformed_request
    EXCEPTION -> R.string.unexpected_error
    USER_NOT_FOUND -> R.string.user_not_found
    WRONG_CREDENTIALS -> R.string.wrong_password_or_nick
    USER_NOT_VERIFIED -> R.string.user_not_verified
    USER_TOKEN_INVALID -> R.string.user_token_invalid
    USER_DELETED -> R.string.user_deleted
    NICKNAME_REJECTED -> R.string.nickname_rejected
    EMAIL_REJECTED -> R.string.email_rejected
    PASSWORD_REJECTED -> R.string.password_rejected
    TOKEN_EXPIRED -> R.string.token_expired
    USER_UPLOAD_FAILED -> R.string.user_upload_failed
    RECIPE_NOT_FOUND -> R.string.recipe_not_found
    RECIPE_DELETED -> R.string.recipe_deleted
    RECIPE_NOT_CREATED -> R.string.recipe_not_created
    CHAT_NOT_FOUND -> R.string.chat_not_found
    CHAT_DELETED -> R.string.chat_deleted
    CHAT_NOT_CREATED -> R.string.chat_not_created
    TOPIC_NOT_FOUND -> R.string.topic_not_found
    TOPIC_DELETED -> R.string.topic_deleted
    ANSWER_NOT_ADDED -> R.string.answer_not_added
    TOPIC_NOT_CREATED -> R.string.topic_not_created
    COMMENT_NOT_FOUND -> R.string.comment_not_found
    COMMENT_DELETED -> R.string.comment_deleted
    POST_NOT_FOUND -> R.string.post_not_found
    POST_DELETED -> R.string.post_deleted
    else -> R.string.unexpected_error
}

fun <T> Throwable?.toAction(): Action<T> = when (this) {
    is UnknownHostException -> Action.Empty(NO_INTERNET)
    is ConnectException -> Action.Empty(CONNECTION_TIMED_OUT)
    is SocketTimeoutException -> Action.Empty(READ_TIMEOUT)
    else -> Action.Error(this?.message)
}