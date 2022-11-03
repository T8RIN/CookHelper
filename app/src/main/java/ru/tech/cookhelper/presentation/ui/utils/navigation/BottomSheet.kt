package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class BottomSheet : Parcelable {
    @Parcelize
    object ForumFilter : BottomSheet()
}