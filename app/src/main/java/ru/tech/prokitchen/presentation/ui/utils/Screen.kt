package ru.tech.prokitchen.presentation.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String = "",
    val shortTitle: String = "",
    val baseIcon: ImageVector = Icons.Outlined.PhoneAndroid,
    val selectedIcon: ImageVector = Icons.Rounded.PhoneAndroid
) {
    object Cuisine : Screen(
        route = "cuisine",
        title = "Банк рецептов",
        shortTitle = "Рецепты",
        baseIcon = Icons.Outlined.DinnerDining,
        selectedIcon = Icons.Filled.DinnerDining
    )

    object Favourites : Screen(
        route = "favourites",
        title = "Любимые блюда",
        shortTitle = "Избранное",
        baseIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite
    )

    object Fridge : Screen(
        route = "fridge",
        title = "Холодильник",
        shortTitle = "Холодильник"
    )
}
