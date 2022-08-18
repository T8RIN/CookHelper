package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.DialogNavHost
import dev.olshevski.navigation.reimagined.NavController
import ru.tech.cookhelper.presentation.recipe_post_creation.components.CategorySelectionDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LeaveUnsavedDataDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.PickProductsWithMeasuresDialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DialogNavigationBox(controller: NavController<Dialog>) {
    DialogNavHost(controller = controller) { dialog ->
        when (dialog) {
            is Dialog.Exit -> {
                ExitDialog(onExit = dialog.onExit)
            }
            is Dialog.AboutApp -> {
                AboutAppDialog()
            }
            is Dialog.Logout -> {
                LogoutDialog(onLogout = dialog.onLogout)
            }
            is Dialog.PickProducts -> {
                TODO()
            }
            is Dialog.CategorySelection -> {
                CategorySelectionDialog(
                    categories = dialog.categories,
                    selectedCategory = dialog.selectedCategory,
                    onCategorySelected = dialog.onCategorySelected
                )
            }
            is Dialog.LeaveUnsavedData -> {
                LeaveUnsavedDataDialog(
                    title = dialog.title,
                    message = dialog.message,
                    onLeave = dialog.onLeave
                )
            }
            is Dialog.PickProductsWithMeasures -> {
                PickProductsWithMeasuresDialog(
                    products = dialog.products,
                    allProducts = dialog.allProducts,
                    onProductsPicked = dialog.onProductsPicked
                )
            }
            else -> {}
        }
    }
}