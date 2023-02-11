package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.NavTransitionSpec
import ru.tech.cookhelper.presentation.all_images.AllImagesScreen
import ru.tech.cookhelper.presentation.authentication.AuthenticationScreen
import ru.tech.cookhelper.presentation.chat.ChatScreen
import ru.tech.cookhelper.presentation.chat_list.ChatListScreen
import ru.tech.cookhelper.presentation.edit_profile.EditProfileScreen
import ru.tech.cookhelper.presentation.forum_discussion.ForumDiscussionScreen
import ru.tech.cookhelper.presentation.fullscreen_image_pager.FullScreenPagerScreen
import ru.tech.cookhelper.presentation.home_screen.HomeScreen
import ru.tech.cookhelper.presentation.matched_recipes.MatchedRecipesScreen
import ru.tech.cookhelper.presentation.post_creation.PostCreationScreen
import ru.tech.cookhelper.presentation.profile.ProfileScreen
import ru.tech.cookhelper.presentation.recipe_details.RecipeDetailsScreen
import ru.tech.cookhelper.presentation.recipe_post_creation.RecipePostCreationScreen
import ru.tech.cookhelper.presentation.settings.SettingsScreen
import ru.tech.cookhelper.presentation.topic_creation.TopicCreationScreen
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalSettingsProvider
import ru.tech.cookhelper.presentation.ui.widgets.Placeholder

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScreenHost(
    controller: NavController<Screen>,
    scrollBehavior: TopAppBarScrollBehavior,
    transitionSpec: NavTransitionSpec<Any?>,
    onTitleChange: (newTitle: UIText) -> Unit,
) {
    AnimatedNavHost(
        controller = controller,
        transitionSpec = transitionSpec
    ) { screen ->
        Box(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            when (screen) {
                is Screen.Home -> {
                    HomeScreen(onTitleChange = onTitleChange, scrollBehavior = scrollBehavior)
                }
                is Screen.Favourites -> Placeholder(
                    screen.baseIcon,
                    screen.title.asString()
                )
                is Screen.RecipeDetails -> {
                    RecipeDetailsScreen(
                        recipe = screen.recipe,
                        percentString = screen.percentString
                    )
                }
                is Screen.MatchedRecipes -> {
                    MatchedRecipesScreen()
                }
                is Screen.FullscreenImagePager -> {
                    FullScreenPagerScreen(
                        imageList = screen.images,
                        initialId = screen.id
                    )
                }
                is Screen.Settings -> {
                    SettingsScreen(settingsState = LocalSettingsProvider.current)
                }
                is Screen.Profile -> {
                    ProfileScreen()
                }
                is Screen.AllImages -> {
                    AllImagesScreen(
                        initial = screen.images,
                        canAddImages = screen.canAddImages,
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
                        image = screen.imageUrl,
                        title = screen.chatTitle,
                        chatId = screen.chatId
                    )
                }
                is Screen.Authentication -> {
                    AuthenticationScreen(onTitleChange = onTitleChange)
                }
                is Screen.PostCreation -> {
                    PostCreationScreen(
                        /*TODO: Remove this shit*/
                        todoRemoveThisFuckingCostyl = screen.todoRemoveThisFuckingCostyl,
                        initialImageUri = screen.imageUri
                    )
                }
                is Screen.RecipePostCreation -> {
                    RecipePostCreationScreen()
                }
                Screen.EditProfile -> {
                    EditProfileScreen()
                }
                Screen.Recipes -> {
                    Placeholder(
                        screen.baseIcon,
                        screen.title.asString()
                    )
                }
                is Screen.ForumDiscussion -> {
                    ForumDiscussionScreen(
                        id = screen.id,
                        title = screen.label
                    )
                }
                Screen.TopicCreation -> {
                    TopicCreationScreen()
                }
                else -> Unit
            }
        }
    }
}