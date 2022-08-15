package ru.tech.cookhelper.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val link: String,
    val id: String
) : Parcelable
