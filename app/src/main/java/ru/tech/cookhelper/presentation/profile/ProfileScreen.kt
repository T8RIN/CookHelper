package ru.tech.cookhelper.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.twotone.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.utils.ConnectionUtils.isOnline
import ru.tech.cookhelper.presentation.profile.components.*
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.compose.navigationBarsLandscapePadding
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalTopAppBarVisuals
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val screenController = LocalScreenController.current
    val activity = LocalContext.current.findActivity()
    val toastHost = LocalToastHostState.current

    val userState = viewModel.userState
    val nick = userState.user?.nickname

    var showAvatarDialog by rememberSaveable { mutableStateOf(false) }
    var showStatusDialog by rememberSaveable { mutableStateOf(false) }
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }

    LocalTopAppBarVisuals.current.update {
        actions {
            IconButton(
                onClick = { showLogoutDialog = true },
                content = { Icon(Icons.Outlined.Logout, null) }
            )
        }
        nick?.let {
            title {
                Text(
                    text = it,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    LazyColumn(
        contentPadding = WindowInsets.navigationBars.asPaddingValues().addPadding(bottom = 92.dp)
    ) {
        item {
            UserInfoBlock(
                userState = userState,
                onEdit = { screenController.navigate(Screen.EditProfile) },
                onStatusUpdate = { showStatusDialog = true },
                onAvatarClick = { showAvatarDialog = true }
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
            if (viewModel.posts.isNotEmpty()) {
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
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .navigationBarsLandscapePadding(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Icon(Icons.TwoTone.CloudOff, null, modifier = Modifier.size(96.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.no_posts), textAlign = TextAlign.Center)
                    }
                }
            }
        } else {
            //TODO: Recipes
        }
    }

    if (showAvatarDialog) {
        PickOrOpenAvatarDialog(
            hasAvatar = userState.user?.avatar?.isNotEmpty() == true,
            onAvatarPicked = { viewModel.addAvatar(it) },
            onOpenAvatar = {
                screenController.navigate(
                    Screen.FullscreenImagePager(
                        images = userState.user?.avatar!!,
                        id = userState.user.avatar.first().id
                    )
                )
            },
            onDismissRequest = { showAvatarDialog = false }
        )
    } else if (showStatusDialog) {
        EditStatusDialog(
            currentStatus = userState.user?.status ?: "",
            onDone = { viewModel.updateStatus(it) },
            onDismissRequest = { showStatusDialog = false }
        )
    } else if (showLogoutDialog) {
        LogoutDialog(
            onLogout = {
                if (activity?.isOnline() == true) {
                    viewModel.logOut()
                } else toastHost.show(
                    Icons.Outlined.SignalWifiConnectedNoInternet4,
                    message = activity?.getString(R.string.no_connection) ?: ""
                )
            },
            onDismissRequest = { showLogoutDialog = false }
        )
    }
}