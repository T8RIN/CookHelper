package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.DialogNavHost
import ru.tech.cookhelper.presentation.pick_products.PickProductsDialog
import ru.tech.cookhelper.presentation.profile.components.EditStatusDialog
import ru.tech.cookhelper.presentation.profile.components.LogoutDialog
import ru.tech.cookhelper.presentation.profile.components.PickOrOpenAvatarDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.CategorySelectionDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.LeaveUnsavedDataDialog
import ru.tech.cookhelper.presentation.recipe_post_creation.components.PickProductsWithMeasuresDialog
import ru.tech.cookhelper.presentation.settings.components.AboutAppDialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.DialogController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DialogHost(controller: DialogController) {
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
                PickProductsDialog(
                    onPicked = dialog.onPicked
                )
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
            is Dialog.EditStatus -> {
                EditStatusDialog(
                    currentStatus = dialog.currentStatus,
                    onDone = dialog.onDone
                )
            }
            is Dialog.PickOrOpenAvatar -> {
                PickOrOpenAvatarDialog(
                    hasAvatar = dialog.hasAvatar,
                    onOpenAvatar = dialog.onOpenAvatar,
                    onAvatarPicked = dialog.onAvatarPicked
                )
            }
        }
    }
}