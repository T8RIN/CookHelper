package ru.tech.cookhelper.presentation.ui.utils.android

import android.util.Log

object Logger {

    fun makeLog(
        vararg any: Any?,
        tag: String = this::class.java.simpleName,
        level: Level = Level.DEBUG
    ) {
        any.forEach {
            when (level) {
                Level.VERBOSE -> Log.v(tag, it.toString())
                Level.DEBUG -> Log.d(tag, it.toString())
                Level.INFO -> Log.i(tag, it.toString())
                Level.WARN -> Log.w(tag, it.toString())
                Level.ERROR -> Log.e(tag, it.toString())
            }
        }
    }

    enum class Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

}