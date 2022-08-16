package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ReflectionUtils.name
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.ui.theme.Fridge
import ru.tech.cookhelper.presentation.ui.utils.UIText

sealed class Screen(
    open val title: UIText = UIText.Empty(),
    open val shortTitle: UIText = UIText.Empty(),
    open val baseIcon: ImageVector = Icons.Default.PhoneAndroid,
    open val selectedIcon: ImageVector = Icons.Default.PhoneAndroid
) : Parcelable {

    @Parcelize
    object Favourites : Screen(
        title = UIText.StringResource(R.string.favourite_dishes),
        shortTitle = UIText.StringResource(R.string.favourites),
        baseIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite
    )

    @Parcelize
    object Profile : Screen(
        title = UIText.StringResource(R.string.profile),
        shortTitle = UIText.StringResource(R.string.profile),
        baseIcon = Icons.Outlined.AccountCircle,
        selectedIcon = Icons.Filled.AccountCircle
    )

    @Parcelize
    object BlockList : Screen(
        title = UIText.StringResource(R.string.block_list),
        shortTitle = UIText.StringResource(R.string.block_list),
        baseIcon = Icons.Outlined.Report,
        selectedIcon = Icons.Filled.Report
    )

    sealed class Home(
        override val title: UIText = UIText.StringResource(R.string.home),
        override val shortTitle: UIText = UIText.StringResource(R.string.home),
        override val baseIcon: ImageVector = Icons.Default.Home,
        override val selectedIcon: ImageVector = Icons.Default.Home
    ) : Screen() {
        @Parcelize
        object Recipes : Home(
            title = UIText.StringResource(R.string.recipe_bank),
            shortTitle = UIText.StringResource(R.string.recipes),
            baseIcon = Icons.Outlined.DinnerDining,
            selectedIcon = Icons.Filled.DinnerDining
        )

        @Parcelize
        object Forum : Home(
            title = UIText.StringResource(R.string.forum_title),
            shortTitle = UIText.StringResource(R.string.forum),
            baseIcon = Icons.Outlined.Forum,
            selectedIcon = Icons.Filled.Forum
        )

        @Parcelize
        object Fridge : Home(
            title = UIText.StringResource(R.string.fridge),
            shortTitle = UIText.StringResource(R.string.fridge),
            baseIcon = Icons.Outlined.Fridge,
            selectedIcon = Icons.Rounded.Fridge
        )

        @Parcelize
        object None : Home()
    }

    @Parcelize
    object Settings : Screen(
        title = UIText.StringResource(R.string.settings),
        shortTitle = UIText.StringResource(R.string.settings),
        baseIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )

    @Parcelize
    object Cart : Screen(
        title = UIText.StringResource(R.string.shopping_list),
        shortTitle = UIText.StringResource(R.string.shopping_list),
        baseIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart
    )

    @Parcelize
    object ChatList : Screen(
        title = UIText.StringResource(R.string.messages),
        shortTitle = UIText.StringResource(R.string.messages),
        baseIcon = Icons.Outlined.Message,
        selectedIcon = Icons.Filled.Message
    )

    @Parcelize
    class RecipeDetails(
        val id: Int = 0,
    ) : Screen()

    @Parcelize
    class MatchedRecipes : Screen()

    @Parcelize
    object Authentication : Screen()

    @Parcelize
    class FullscreenImagePager(
        val id: String = "0",
        val images: List<Image> = emptyList(),
    ) : Screen()


    @Parcelize
    class AllImages(
        val images: List<Image> = emptyList(),
        val canAddImages: Boolean = false,
        val onAddImage: (uri: String) -> Unit
    ) : Screen()

    @Parcelize
    class PostCreation(
        val imageUri: String = ""
    ) : Screen()

    @Parcelize
    class RecipePostCreation : Screen()

    @Parcelize
    class Chat(
        val chatId: String,
    ) : Screen()
}

val drawerList = listOf(
    Screen.Home.None,
    Screen.Profile,
    Screen.ChatList,
    Screen.Cart,
    Screen.Favourites,
    Screen.BlockList,
    Screen.Settings
)

val hideTopBarList = listOf(
    Screen.AllImages::class.name,
    Screen.FullscreenImagePager::class.name,
    Screen.PostCreation::class.name,
    Screen.Chat::class.name,
    Screen.RecipePostCreation::class.name,
    Screen.MatchedRecipes::class.name,
    Screen.RecipeDetails::class.name,
    Screen.Authentication::class.name
)

val navBarList = listOf(
    Screen.Home.Recipes,
    Screen.Home.Forum,
    Screen.Home.Fridge
)
