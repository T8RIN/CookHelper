package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.domain.model.ForumFilters

sealed class BottomSheet(
    val gesturesEnabled: Boolean = true,
    val dismissOnTapOutside: Boolean = true,
    val nestedScrollEnabled: Boolean = true
) : Parcelable {
    @Parcelize
    class ForumFilter(
        @IgnoredOnParcel val filters: ForumFilters = ForumFilters.empty(),
        @IgnoredOnParcel val onFiltersChange: (ForumFilters) -> Unit = {}
    ) : BottomSheet(
        gesturesEnabled = false,
        dismissOnTapOutside = false,
        nestedScrollEnabled = false
    )
}