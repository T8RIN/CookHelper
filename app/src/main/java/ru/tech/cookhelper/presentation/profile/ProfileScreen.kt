package ru.tech.cookhelper.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import dev.olshevski.navigation.reimagined.navigate
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ConnectionUtils.isOnline
import ru.tech.cookhelper.domain.model.*
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.profile.components.*
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*
import kotlin.random.Random.Default.nextBoolean

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    updateTitle: (title: String) -> Unit
) {
    val screenController = LocalScreenController.current
    val dialogController = LocalDialogController.current
    val activity = LocalContext.current.findActivity()

    LocalTopAppBarActions.current.setActions {
        val toastHost = LocalToastHost.current
        IconButton(
            onClick = {
                dialogController.show(
                    Dialog.Logout(
                        onLogout = {
                            if (activity?.isOnline() == true) {
                                viewModel.logOut()
                            } else toastHost.sendToast(
                                Icons.Outlined.SignalWifiConnectedNoInternet4,
                                message = activity?.getString(R.string.no_connection) ?: ""
                            )
                        }
                    )
                )
            },
            content = { Icon(Icons.Outlined.Logout, null) }
        )
    }

    val userState = viewModel.userState
    val nick = userState.user?.nickname
    if (nick != null) LaunchedEffect(Unit) { updateTitle(nick) }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    LazyColumn(
        contentPadding = WindowInsets.navigationBars.asPaddingValues().addPadding(bottom = 80.dp)
    ) {
        item {
            UserInfoBlock(
                userState = userState,
                onEdit = { /*TODO: Edit Profile*/ },
                onStatusUpdate = { /*TODO: UpdateStatus*/ }
            )
            Spacer(Modifier.height(20.dp))
            ImageCarousel(
                data = testList,
                onImageClick = { id ->
                    screenController.navigate(
                        Screen.FullscreenImagePager(
                            id = id,
                            images = testList,
                        )
                    )
                },
                onAddImageClick = {
                    //TODO: Add Image Feature
                },
                onExpand = {
                    screenController.navigate(
                        Screen.AllImages(
                            images = testList,
                            canAddImages = true,
                            onAddImage = {
                                //TODO: Send picked image to server
                            }
                        )
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
            PostCreationBlock(
                onCreateRecipe = {
                    screenController.navigate(Screen.RecipePostCreation)
                }, onCreatePost = { imageUri ->
                    screenController.navigate(Screen.PostCreation(imageUri))
                }
            )
            Spacer(Modifier.height(10.dp))
            FlexibleTabRow(
                selectedTabIndex = selectedTabIndex,
                tabs = listOf(stringResource(R.string.posts), stringResource(R.string.recipes)),
                divider = { Separator() },
                onTabClick = { selectedTabIndex = it }
            )
            Spacer(Modifier.height(20.dp))
        }

        if (selectedTabIndex.toTab() == SelectedTab.Posts) {
            val posts = listOf(
                Post(
                    "1",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = false,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 122,
                    commentsCount = 32,
                    image = null
                ),
                Post(
                    "2",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 233,
                    commentsCount = 1535,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "6"
                    )
                ),
                Post(
                    "3",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = null,
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 156,
                    commentsCount = 12,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "7"
                    )
                ),
                Post(
                    "4",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 233,
                    commentsCount = 1535,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "6"
                    )
                ),
                Post(
                    "5",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 233,
                    commentsCount = 1535,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "6"
                    )
                ),
                Post(
                    "6",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 233,
                    commentsCount = 1535,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "6"
                    )
                ),
                Post(
                    "7",
                    "2",
                    timestamp = System.currentTimeMillis(),
                    title = "Мем года",
                    liked = true,
                    text = "Вампиры суициндики долбят чеснок",
                    likeCount = 233,
                    commentsCount = 1535,
                    image = Image(
                        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                        "6"
                    )
                ),
            )
            itemsIndexed(posts, key = { _, post -> post.postId }) { index, post ->
                PostItem(
                    post = post,
                    authorLoader = {
                        User(
                            id = 1,
                            avatar = "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                            name = "Малик",
                            surname = "Мухаметзянов",
                            token = "",
                            email = "",
                            nickname = "t8rin",
                            verified = true,
                            lastSeen = 0L
                        )
                    },
                    onImageClick = { id ->
                        screenController.navigate(
                            Screen.FullscreenImagePager(
                                id = id,
                                images = post.image?.let { listOf(it) } ?: emptyList(),
                            )
                        )
                    },
                    onAuthorClick = {
                        //TODO: Open Author page
                    },
                    onPostClick = {
                        //TODO: Open Post fullscreen
                    },
                    onLikeClick = {
                        //TODO: Like post feature
                    }
                )
                if (posts.lastIndex != index) {
                    Separator(thickness = 8.dp, modifier = Modifier.alpha(0.2f))
                }
            }
        } else {
            items(6, key = { /*TODO: set normal key*/ }) {
                ProfileRecipeItem(
                    RecipePost(
                        "", "", 0L, nextBoolean(), 29, 12,
                        Recipe(
                            0,
                            listOf(
                                "cucumber",
                                "apple",
                                "pineapple",
                                "vinegar",
                                "pepper",
                                "salt",
                                "jalapeno",
                                "meat",
                                "cucumber",
                                "apple",
                                "pineapple",
                                "vinegar",
                                "pepper",
                                "salt",
                                "jalapeno",
                                "meat"
                            ),
                            emptyList(),
                            0.0,
                            0.0,
                            "",
                            emptyList(),
                            100,
                            0.0,
                            0.0,
                            "",
                            "Big and hard to cook recipe with very very very long label",
                            "https://koelov.ru/wp-content/uploads/2013/10/kabachkovyj-tort-recept-prigotovleniya.jpg"
                        )
                    ),
                    onRecipeClick = {
                        //TODO: Open Recipe fullscreen like a post
                    },
                    onAuthorClick = {
                        //TODO: Open Author page
                    },
                    authorLoader = {
                        User(
                            id = 1,
                            avatar = "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                            name = "Малик",
                            surname = "Мухаметзянов",
                            token = "",
                            email = "",
                            nickname = "t8rin",
                            verified = true,
                            lastSeen = 0L
                        )
                    }
                )
            }
        }
    }
}

private val testList = listOf(
    Image("https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg", "1"),
    Image(
        "https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg",
        "2"
    ),
    Image(
        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
        "3"
    ),
    Image("https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg", "4"),
    Image(
        "https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg",
        "5"
    )
)