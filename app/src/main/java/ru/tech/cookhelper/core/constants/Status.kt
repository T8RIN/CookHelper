package ru.tech.cookhelper.core.constants

object Status {
    const val SUCCESS: Int = 100
    const val WRONG_DATA: Int = 99
    const val PERMISSION_DENIED: Int = 98
    const val PARAMETER_MISSED: Int = 97
    const val EXCEPTION: Int = 0
    const val NO_INTERNET = -1
    const val CONNECTION_TIMED_OUT = -2
    const val READ_TIMEOUT = -3

    const val USER_NOT_FOUND: Int = 101
    const val WRONG_CREDENTIALS: Int = 102
    const val USER_NOT_VERIFIED: Int = 103
    const val USER_TOKEN_INVALID: Int = 104
    const val USER_DELETED: Int = 105
    const val NICKNAME_REJECTED: Int = 106
    const val EMAIL_REJECTED: Int = 107
    const val PASSWORD_REJECTED: Int = 108
    const val TOKEN_EXPIRED: Int = 109
    const val USER_UPLOAD_FAILED: Int = 110

    const val RECIPE_NOT_FOUND: Int = 201
    const val RECIPE_DELETED: Int = 202

    const val TOPIC_NOT_FOUND: Int = 401
    const val TOPIC_DELETED: Int = 402
    const val ANSWER_NOT_ADDED: Int = 403

    const val COMMENT_NOT_FOUND: Int = 801
    const val COMMENT_DELETED: Int = 802

    const val POST_NOT_FOUND: Int = 901
    const val POST_DELETED: Int = 902
}