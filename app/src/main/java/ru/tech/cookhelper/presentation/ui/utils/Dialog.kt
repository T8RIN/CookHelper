package ru.tech.cookhelper.presentation.ui.utils

import androidx.annotation.StringRes
import ru.tech.cookhelper.domain.model.Product

sealed class Dialog {

    object None : Dialog()

    object Exit : Dialog()

    object AboutApp : Dialog()

    object PickProducts : Dialog()

    object Logout : Dialog()

    class CategorySelection(
        val categories: List<String>,
        val selectedCategory: String,
        val onCategorySelected: (category: String) -> Unit
    ) : Dialog()

    class LeaveUnsavedData(
        @StringRes val title: Int,
        @StringRes val message: Int,
        val onLeave: () -> Unit
    ) : Dialog()

    class PickProductsWithMeasures(
        val products: List<Product>,
        val allProducts: List<Product>,
        val onProductsPicked: (newProducts: List<Product>) -> Unit
    ) : Dialog()
}
