package ru.tech.cookhelper.presentation.ui.utils.android

import android.util.Log

object Logger {

    fun makeLog(
        any: Any?,
        tag: String = this::class.java.simpleName,
        level: Level = Level.DEBUG
    ) {
        when (level) {
            Level.VERBOSE -> Log.v(tag, any.toString())
            Level.DEBUG -> Log.d(tag, any.toString())
            Level.INFO -> Log.i(tag, any.toString())
            Level.WARN -> Log.w(tag, any.toString())
            Level.ERROR -> Log.e(tag, any.toString())
        }
    }

    fun Any?.log(
        tag: String = (this@Logger)::class.java.simpleName,
        level: Level = Level.DEBUG
    ) {
        makeLog(
            any = this,
            tag = tag,
            level = level
        )
    }

    enum class Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

}