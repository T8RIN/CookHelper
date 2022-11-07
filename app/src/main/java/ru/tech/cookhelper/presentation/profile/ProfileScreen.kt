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
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ConnectionUtils.isOnline
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.profile.components.*
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*

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
                onEdit = { screenController.navigate(Screen.EditProfile) },
                onStatusUpdate = { currentStatus ->
                    dialogController.show(
                        Dialog.EditStatus(
                            currentStatus = currentStatus,
                            onDone = { viewModel.updateStatus(it) }
                        )
                    )
                },
                onAvatarClick = { /* TODO: avatarList -> */
                    val hasAvatar = /*avatarList?.isNotEmpty == true*/ true
                    dialogController.show(
                        Dialog.PickOrOpenAvatar(
                            hasAvatar = hasAvatar,
                            onAvatarPicked = { viewModel.addAvatar(it) },
                            onOpenAvatar = { /*TODO: OpenAvatar*/ }
                        )
                    )
                }
            )
            Spacer(Modifier.height(20.dp))
            ImageCarousel(
                data = emptyList(),
                onImageClick = { id ->
                    screenController.navigate(
                        Screen.FullscreenImagePager(
                            id = id,
                            images = emptyList(),
                        )
                    )
                },
                onAddImage = { viewModel.addImage(it) },
                onExpand = {
                    screenController.navigate(
                        Screen.AllImages(
                            images = emptyList(),
                            canAddImages = true,
                            onAddImage = { viewModel.addImage(it) }
                        )
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
            PostCreationBlock(
                onCreateRecipe = {
                    screenController.navigate(Screen.RecipePostCreation)
                }, onCreatePost = { imageUri ->
                    screenController.navigate(
                        Screen.PostCreation(imageUri) {
                            /*TODO: Remove this shit*/
                            it?.let {
                                val l = listOf(it) + viewModel.posts
                                viewModel.posts.clear()
                                viewModel.posts.addAll(l)
                            }
                        }
                    )
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
            itemsIndexed(viewModel.posts, key = { _, post -> post.id }) { index, post ->
                PostItem(
                    post = post,
                    onImageClick = {
                        screenController.navigate(
                            Screen.FullscreenImagePager(
                                id = it.id,
                                images = post.images
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
                    },
                    clientUserId = userState.user?.id ?: 0L
                )
//                TODO(Get posts)
                /*TODO: Remove this shit*/
                if (viewModel.posts.lastIndex != index) {
                    Separator(thickness = 8.dp, modifier = Modifier.alpha(0.2f))
                }
            }
        } else {
            //TODO: Recipes
        }
    }
}