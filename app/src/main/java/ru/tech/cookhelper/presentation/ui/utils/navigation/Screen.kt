package ru.tech.cookhelper.presentation.ui.utils.navigation

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.ui.theme.Fridge
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText

sealed class Screen(
    open val title: UIText = UIText.Empty(),
    open val shortTitle: UIText = UIText.Empty(),
    open val baseIcon: ImageVector = Icons.Default.PhoneAndroid,
    open val selectedIcon: ImageVector = Icons.Default.PhoneAndroid,
    open val showTopAppBar: Boolean = true
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
        override val baseIcon: ImageVector = Icons.Outlined.Home,
        override val selectedIcon: ImageVector = Icons.Filled.Home
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
    ) : Screen(showTopAppBar = false)

    @Parcelize
    object MatchedRecipes : Screen(showTopAppBar = false)

    @Parcelize
    sealed class Authentication : Screen(showTopAppBar = false) {
        object Login : Authentication()
        object Register : Authentication()
        object Confirmation : Authentication()
        object RestorePassword : Authentication()
    }

    @Parcelize
    class FullscreenImagePager(
        val id: String = "0",
        val images: List<Image> = emptyList()
    ) : Screen(showTopAppBar = false)


    @Parcelize
    class AllImages(
        val images: List<Image> = emptyList(),
        val canAddImages: Boolean = false,
        @IgnoredOnParcel val onAddImage: (uri: String) -> Unit = {}
    ) : Screen(showTopAppBar = false)

    @Parcelize
    class PostCreation(
        val imageUri: String = ""
    ) : Screen(showTopAppBar = false)

    @Parcelize
    object RecipePostCreation : Screen(showTopAppBar = false)

    @Parcelize
    class Chat(
        val chatId: String,
    ) : Screen(showTopAppBar = false)
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

val navBarList = listOf(
    Screen.Home.Recipes,
    Screen.Home.Forum,
    Screen.Home.Fridge
)
