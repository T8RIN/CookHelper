package ru.tech.cookhelper.presentation.ui.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.tech.cookhelper.R

sealed class Screen(
    @StringRes val title: Int = R.string.app_name,
    @StringRes val shortTitle: Int = R.string.app_name,
    val baseIcon: ImageVector = Icons.Outlined.PhoneAndroid,
    val selectedIcon: ImageVector = Icons.Rounded.PhoneAndroid
) {

    object Forum : Screen(
        title = R.string.forum_title,
        shortTitle = R.string.forum,
        baseIcon = Icons.Outlined.Forum,
        selectedIcon = Icons.Filled.Forum
    )

    object Recipes : Screen(
        title = R.string.recipe_bank,
        shortTitle = R.string.recipes,
        baseIcon = Icons.Outlined.DinnerDining,
        selectedIcon = Icons.Filled.DinnerDining
    )

    object Favourites : Screen(
        title = R.string.fav_dishes,
        shortTitle = R.string.fav,
        baseIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite
    )

    object Fridge : Screen(
        title = R.string.fridge,
        shortTitle = R.string.fridge
    )

    object Profile : Screen(
        title = R.string.profile,
        shortTitle = R.string.profile,
        baseIcon = Icons.Outlined.AccountCircle,
        selectedIcon = Icons.Filled.AccountCircle
    )

    object BlockList : Screen(
        title = R.string.blockList,
        shortTitle = R.string.blockList,
        baseIcon = Icons.Outlined.Report,
        selectedIcon = Icons.Filled.Report
    )

    object Home : Screen(
        title = R.string.home,
        shortTitle = R.string.home,
        baseIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )

    object Settings : Screen(
        title = R.string.settings,
        shortTitle = R.string.settings,
        baseIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )

    object Cart : Screen(
        title = R.string.shopping_list,
        shortTitle = R.string.shopping_list,
        baseIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart
    )

    object Messages : Screen(
        title = R.string.messages,
        shortTitle = R.string.messages,
        baseIcon = Icons.Outlined.Message,
        selectedIcon = Icons.Filled.Message
    )

    class RecipeDetails(val id: Int = 0, val previousScreen: Screen = Home) : Screen()

    class MatchedRecipes(val previousScreen: Screen) : Screen()

    @Composable
    fun Int.asString(): String {
        return stringResource(id = this)
    }
}

val drawerList = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Messages,
    Screen.Cart,
    Screen.Favourites,
    Screen.BlockList,
    Screen.Settings
)

val navBarList = listOf(
    Screen.Recipes,
    Screen.Forum,
    Screen.Fridge
)
