package ru.tech.cookhelper.presentation.ui.utils.android

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.Extensions.GIF
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.Extensions.SVG
import java.io.File
import java.io.FileOutputStream


object ImageUtils {

    object Extensions {
        const val SVG = ".svg"
        const val GIF = ".gif"
    }

    infix fun ExifInterface.copyTo(newExif: ExifInterface) {
        listOf(
            ExifInterface.TAG_DATETIME,
            ExifInterface.TAG_DATETIME_DIGITIZED,
            ExifInterface.TAG_EXPOSURE_TIME,
            ExifInterface.TAG_FLASH,
            ExifInterface.TAG_FOCAL_LENGTH,
            ExifInterface.TAG_GPS_ALTITUDE,
            ExifInterface.TAG_GPS_ALTITUDE_REF,
            ExifInterface.TAG_GPS_DATESTAMP,
            ExifInterface.TAG_GPS_LATITUDE,
            ExifInterface.TAG_GPS_LATITUDE_REF,
            ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_GPS_LONGITUDE_REF,
            ExifInterface.TAG_GPS_PROCESSING_METHOD,
            ExifInterface.TAG_GPS_TIMESTAMP,
            ExifInterface.TAG_IMAGE_LENGTH,
            ExifInterface.TAG_IMAGE_WIDTH,
            ExifInterface.TAG_MAKE,
            ExifInterface.TAG_MODEL,
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.TAG_SUBSEC_TIME,
            ExifInterface.TAG_WHITE_BALANCE
        ).forEach { attr ->
            getAttribute(attr)?.let { newExif.setAttribute(attr, it) }
        }
        newExif.saveAttributes()
    }

    val File?.isSvg: Boolean
        get() {
            return this?.name?.endsWith(SVG) == true
        }

    val File?.isGif: Boolean
        get() {
            return this?.name?.endsWith(GIF) == true
        }

    suspend fun File.compress(
        saveExif: Boolean = true,
        quality: Int = 50,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        condition: (File) -> Boolean = { true }
    ): Boolean = withContext(dispatcher) {
        val oldExif = ExifInterface(this@compress)
        return@withContext runCatching {
            if (condition(this@compress)) {
                BitmapFactory.decodeFile(path)
                    .compress(
                        format, quality,
                        FileOutputStream(this@compress)
                    ).also {
                        if (saveExif) oldExif copyTo ExifInterface(this@compress)
                    }
            } else false
        }.getOrNull() ?: false
    }

}