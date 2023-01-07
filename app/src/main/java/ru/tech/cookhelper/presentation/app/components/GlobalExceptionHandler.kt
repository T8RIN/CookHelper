package ru.tech.cookhelper.presentation.app.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlin.system.exitProcess

class GlobalExceptionHandler<T : Activity> private constructor(
    private val applicationContext: Context,
    private val defaultHandler: Thread.UncaughtExceptionHandler,
    private val activityToBeLaunched: Class<T>
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(p0: Thread, p1: Throwable) {
        try {
            Log.e(this.toString(), p1.stackTraceToString())
            launchActivity(applicationContext, activityToBeLaunched, p1)
            exitProcess(0)
        } catch (e: Exception) {
            defaultHandler.uncaughtException(p0, p1)
        }
    }

    private fun <T : Activity> launchActivity(
        applicationContext: Context,
        activity: Class<T>,
        exception: Throwable
    ) {
        val crashedIntent = Intent(applicationContext, activity).apply {
            putExtra(INTENT_DATA_NAME, "$exception\n${Log.getStackTraceString(exception)}")
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }
        applicationContext.startActivity(crashedIntent)
    }

    companion object {
        private const val INTENT_DATA_NAME = "GlobalExceptionHandler"

        fun <T : Activity> initialize(
            applicationContext: Context,
            activityToBeLaunched: Class<T>
        ) {
            val handler = GlobalExceptionHandler(
                applicationContext,
                Thread.getDefaultUncaughtExceptionHandler() as Thread.UncaughtExceptionHandler,
                activityToBeLaunched
            )
            Thread.setDefaultUncaughtExceptionHandler(handler)
        }

        fun Activity.getExceptionString(): String = intent.getStringExtra(INTENT_DATA_NAME) ?: ""
    }
}