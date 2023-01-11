package ru.tech.cookhelper.presentation.ui.utils.android

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.core.os.LocaleListCompat
import org.xmlpull.v1.XmlPullParser
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.kotlin.applyCatching
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

object ContextUtils {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun Context.getFile(uri: String?): File? {
        if (uri?.isEmpty() == true) return null

        fun Context.queryName(uri: Uri?): String {
            if (uri?.toString()?.isEmpty() == true) return ""

            return contentResolver.query(uri!!, null, null, null, null)?.run {
                getString(moveToFirst().run { getColumnIndex(OpenableColumns.DISPLAY_NAME) }).also { close() }
            } ?: ""
        }

        fun createFileFromStream(ins: InputStream, destination: File?) {
            runCatching {
                FileOutputStream(destination).use { os ->
                    val buffer = ByteArray(4096)
                    var length: Int
                    while (ins.read(buffer).also { length = it } > 0) {
                        os.write(buffer, 0, length)
                    }
                    os.flush()
                }
            }
        }

        return File("${filesDir.path}${File.separatorChar}${queryName(uri?.toUri())}").applyCatching {
            contentResolver.openInputStream(uri!!.toUri())?.use { ins ->
                createFileFromStream(ins, this)
            }
        }
    }

    fun Context.getLanguages(): Map<String, String> {
        val languages = mutableListOf<Pair<String, String>>()
        val parser = resources.getXml(R.xml.locales_config)
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "locale") {
                for (i in 0 until parser.attributeCount) {
                    if (parser.getAttributeName(i) == "name") {
                        val langTag = parser.getAttributeValue(i)
                        val displayName = getDisplayName(langTag)
                        if (displayName.isNotEmpty()) {
                            languages.add(Pair(langTag, displayName))
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        languages.sortBy { it.second }
        languages.add(0, Pair("", getString(R.string.system)))

        return languages.toMap()
    }

    fun Context.getCurrentLocaleString(): String {
        val locales = AppCompatDelegate.getApplicationLocales()
        if (locales == LocaleListCompat.getEmptyLocaleList()) {
            return getString(R.string.system)
        }
        return getDisplayName(locales.toLanguageTags())
    }

    private fun getDisplayName(lang: String?): String {
        if (lang == null) {
            return ""
        }

        val locale = when (lang) {
            "" -> LocaleListCompat.getAdjustedDefault()[0]
            else -> Locale.forLanguageTag(lang)
        }
        return locale!!.getDisplayName(locale).replaceFirstChar { it.uppercase(locale) }
    }

}
