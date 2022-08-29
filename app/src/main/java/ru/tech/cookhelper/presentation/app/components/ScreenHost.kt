package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.AnimatedNavHostTransitionSpec
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.navigate
import ru.tech.cookhelper.presentation.all_images.AllImagesScreen
import ru.tech.cookhelper.presentation.authentication.AuthenticationScreen
import ru.tech.cookhelper.presentation.chat.ChatScreen
import ru.tech.cookhelper.presentation.chat_list.ChatListScreen
import ru.tech.cookhelper.presentation.dish_details.DishDetailsScreen
import ru.tech.cookhelper.presentation.dishes_based_on_fridge.OnFridgeBasedDishes
import ru.tech.cookhelper.presentation.fullscreen_image_pager.FullScreenPagerScreen
import ru.tech.cookhelper.presentation.home_screen.HomeScreen
import ru.tech.cookhelper.presentation.post_creation.PostCreationScreen
import ru.tech.cookhelper.presentation.profile.ProfileScreen
import ru.tech.cookhelper.presentation.recipe_post_creation.RecipePostCreationScreen
import ru.tech.cookhelper.presentation.settings.SettingsScreen
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.android.SystemBarUtils.showSystemBars
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSettingsProvider
import ru.tech.cookhelper.presentation.ui.utils.provider.goBack

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenHost(
    controller: NavController<Screen>,
    nestedScrollConnection: NestedScrollConnection,
    transitionSpec: AnimatedNavHostTransitionSpec<Any?>,
    onTitleChange: (newTitle: UIText) -> Unit,
) {
    val activity = LocalContext.current.findActivity()
    AnimatedNavHost(
        controller = controller,
        transitionSpec = transitionSpec
    ) { screen ->
        Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
            when (screen) {
                is Screen.Home -> {
                    HomeScreen(onTitleChange = onTitleChange)
                }
                is Screen.Favourites -> Placeholder(
                    screen.baseIcon,
                    screen.title.asString()
                )
                is Screen.RecipeDetails -> {
                    DishDetailsScreen(
                        id = screen.id,
                        onBack = { controller.goBack() })
                }
                is Screen.MatchedRecipes -> {
                    OnFridgeBasedDishes(
                        onRecipeClicked = {
                            controller.navigate(
                                Screen.RecipeDetails(it)
                            )
                        },
                        onBack = { controller.goBack() }
                    )
                }
                is Screen.FullscreenImagePager -> {
                    FullScreenPagerScreen(
                        images = screen.images,
                        initialId = screen.id,
                        onBack = {
                            controller.goBack()
                            activity?.showSystemBars()
                        }
                    )
                }
                is Screen.Settings -> {
                    SettingsScreen(settingsState = LocalSettingsProvider.current)
                }
                is Screen.Profile -> {
                    ProfileScreen(
                        updateTitle = { newTitle ->
                            onTitleChange(UIText.DynamicString(newTitle))
                        }
                    )
                }
                is Screen.AllImages -> {
                    AllImagesScreen(
                        images = screen.images,
                        canAddImages = screen.canAddImages,
                        onBack = { controller.goBack() },
                        onAddImage = screen.onAddImage
                    )
                }
                is Screen.BlockList -> Placeholder(
                    screen.baseIcon,
                    screen.title.asString()
                )
                is Screen.Cart -> Placeholder(
                    screen.baseIcon,
                    screen.title.asString()
                )
                is Screen.ChatList -> {
                    ChatListScreen()
                }
                is Screen.Chat -> {
                    ChatScreen(
                        chatId = screen.chatId,
                        onBack = { controller.goBack() }
                    )
                }
                is Screen.Authentication -> {
                    AuthenticationScreen()
                }
                is Screen.PostCreation -> {
                    PostCreationScreen(
                        onBack = {
                            controller.goBack()
                        },
                        initialImageUri = screen.imageUri
                    )
                }
                is Screen.RecipePostCreation -> {
                    RecipePostCreationScreen(
                        onBack = {
                            controller.goBack()
                        }
                    )
                }
            }
        }
    }
}