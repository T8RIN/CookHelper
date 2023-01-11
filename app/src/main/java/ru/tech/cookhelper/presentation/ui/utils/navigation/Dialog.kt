package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.domain.model.Product

sealed class Dialog : Parcelable {

    @Parcelize
    class Exit(
        @IgnoredOnParcel val onExit: () -> Unit = {}
    ) : Dialog()

    @Parcelize
    object AboutApp : Dialog()

    @Parcelize
    class PickProducts(
        @IgnoredOnParcel val onPicked: (products: List<Product>) -> Unit = {}
    ) : Dialog()

    @Parcelize
    class Logout(
        @IgnoredOnParcel val onLogout: () -> Unit = {}
    ) : Dialog()

    @Parcelize
    class CategorySelection(
        val categories: List<String>,
        val selectedCategory: String,
        @IgnoredOnParcel val onCategorySelected: (category: String) -> Unit = {}
    ) : Dialog()

    @Parcelize
    class LeaveUnsavedData(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @IgnoredOnParcel val onLeave: () -> Unit = {}
    ) : Dialog()

    @Parcelize
    class PickProductsWithMeasures(
        @IgnoredOnParcel val products: Map<Product, String> = emptyMap(),
        @IgnoredOnParcel val allProducts: List<Product> = emptyList(),
        @IgnoredOnParcel val onProductsPicked: (newProducts: Map<Product, String>) -> Unit = {}
    ) : Dialog()

    @Parcelize
    class EditStatus(
        val currentStatus: String,
        @IgnoredOnParcel val onDone: (newStatus: String) -> Unit = {}
    ) : Dialog()

    @Parcelize
    class PickOrOpenAvatar(
        val hasAvatar: Boolean,
        @IgnoredOnParcel val onAvatarPicked: (imageUri: String) -> Unit = {},
        @IgnoredOnParcel val onOpenAvatar: () -> Unit = {}
    ) : Dialog()

    @Parcelize
    class PickLanguageDialog(
        @IgnoredOnParcel val entries: Map<String, String> = emptyMap(),
        val selected: String,
        @IgnoredOnParcel val onSelect: (String) -> Unit = {},
        @IgnoredOnParcel val onDismiss: () -> Unit = {}
    ) : Dialog()
}