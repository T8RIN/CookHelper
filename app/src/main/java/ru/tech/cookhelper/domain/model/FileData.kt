package ru.tech.cookhelper.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.domain.utils.Domain

@Parcelize
data class FileData(
    val link: String,
    val id: String
) : Parcelable, Domain
