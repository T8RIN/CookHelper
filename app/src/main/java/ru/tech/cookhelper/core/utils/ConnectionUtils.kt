package ru.tech.cookhelper.core.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object ConnectionUtils {
    fun Context.isOnline(): Boolean {
        (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                getNetworkCapabilities(activeNetwork)
                    ?.run {
                        listOf(
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR),
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI),
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET),
                        ).any { it }
                    } ?: false
            } else @Suppress("DEPRECATION") {
                activeNetworkInfo != null && activeNetworkInfo?.isConnected == true
            }
        }
    }
}