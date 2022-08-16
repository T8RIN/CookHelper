package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.domain.model.Product

sealed class Dialog : Parcelable {

    @Parcelize
    object None : Dialog()

    @Parcelize
    object Exit : Dialog()

    @Parcelize
    object AboutApp : Dialog()

    @Parcelize
    object PickProducts : Dialog()

    @Parcelize
    object Logout : Dialog()

    @Parcelize
    class CategorySelection(
        val categories: List<String>,
        val selectedCategory: String,
        val onCategorySelected: (category: String) -> Unit
    ) : Dialog()

    @Parcelize
    class LeaveUnsavedData(
        @StringRes val title: Int,
        @StringRes val message: Int,
        val onLeave: () -> Unit
    ) : Dialog()

    @Parcelize
    class PickProductsWithMeasures(
        val products: List<Product>,
        val allProducts: List<Product>,
        val onProductsPicked: (newProducts: List<Product>) -> Unit
    ) : Dialog()
}
