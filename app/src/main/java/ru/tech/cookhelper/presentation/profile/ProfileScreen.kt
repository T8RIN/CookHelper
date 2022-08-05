package ru.tech.cookhelper.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.*
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.profile.components.*
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.StateUtils.computedStateOf
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextBoolean

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = scopedViewModel(ignoreDisposing = listOf(Screen.FullscreenImagePager::class)),
    updateTitle: (title: String) -> Unit
) {
    val lazyListState = rememberForeverLazyListState(key = Screen.Profile::class.name)

    val screenController = LocalScreenController.current

    val userState = viewModel.userState.value
    val nick = userState.user?.nickname
    if (nick != null) LaunchedEffect(Unit) { updateTitle(nick) }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    val status = userState.user?.status
    val lastSeen by computedStateOf(userState) {
        val lastSeen = userState.user?.lastSeen ?: 0L
        val df = if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat(
                "yyyy",
                Locale.getDefault()
            )
                .format(lastSeen)
                .toInt()
        ) {
            SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
        } else SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
        df.format(lastSeen)
    }

    LazyColumn(
        state = lazyListState, contentPadding = PaddingValues(
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 80.dp
        )
    ) {
        item {
            Column(Modifier.padding(horizontal = 15.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Picture(
                        model = userState.user?.avatar,
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .size(80.dp),
                        error = {
                            Icon(Icons.Filled.AccountCircle, null)
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(
                        Modifier
                            .padding(top = 15.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            userState.user?.let { "${it.name} ${it.surname}" }.toString(),
                            modifier = Modifier.padding(start = 5.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(450)
                        )
                        Spacer(Modifier.height(5.dp))
                        if (status?.isEmpty() == true) {
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        //TODO: UpdateStatus
                                    }
                            ) {
                                Spacer(Modifier.width(5.dp))
                                Text(
                                    text = stringResource(R.string.set_status),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.width(5.dp))
                            }
                        } else {
                            Text(
                                userState.user?.status.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 5.dp)
                        ) {
                            Icon(
                                Icons.Rounded.PhoneAndroid,
                                null,
                                modifier = Modifier.size(12.dp),
                                tint = Color.Gray
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                text = lastSeen,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO: Edit Profile*/ }) {
                    Text(stringResource(R.string.edit))
                }
            }
        }
        item { Spacer(Modifier.height(20.dp)) }
        item {
            ImageCarousel(
                data = testList,
                onImageClick = { id ->
                    screenController.apply {
                        navigate(
                            Screen.FullscreenImagePager(
                                id = id,
                                images = testList,
                                previousScreen = currentScreen
                            )
                        )
                    }
                },
                onAddImageClick = {
                    //TODO: Add Image Feature
                },
                onExpand = {
                    screenController.navigate(Screen.AllImages(testList, Screen.Profile, true))
                }
            )
        }
        item { Spacer(Modifier.height(30.dp)) }
        item {
            Column(
                Modifier.padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    stringResource(R.string.create).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(Modifier.size(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FilledTonalButton(
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = { /*TODO: Recipe creation*/ }) {
                        Text(stringResource(R.string.recipe))
                    }
                    Spacer(Modifier.size(5.dp))
                    FilledTonalButton(
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = { /*TODO: Post creation*/ }) {
                        Text(stringResource(R.string.post))
                    }
                    Spacer(Modifier.size(5.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .clickable(
                                onClick = { /*TODO: Add image to post instantly*/ },
                                role = Role.Button
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
        item { Spacer(Modifier.height(10.dp)) }
        item {
            val tabs =
                listOf(stringResource(R.string.posts), stringResource(R.string.recipes))
            Column {
                FlexibleTabRow(
                    selectedTabIndex = selectedTabIndex,
                    tabs = tabs,
                    divider = { Divider(color = MaterialTheme.colorScheme.surfaceVariant) },
                    onTabClick = { tabIndex ->
                        selectedTabIndex = tabIndex
                    }
                )
            }
        }
        item { Spacer(Modifier.height(20.dp)) }
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
            )

            itemsIndexed(posts) { index, post ->
                PostItem(
                    post = post,
                    authorLoader = {
                        User(
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
                                previousScreen = screenController.currentScreen
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
                    Divider(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        thickness = 8.dp
                    )
                }
            }
        } else {
            items(6) {
                ProfileRecipeItem(
                    RecipePost(
                        "", "", 0L, nextBoolean(), 29, 12,
                        Recipe(
                            0,
                            emptyList(),
                            emptyList(),
                            0.0,
                            0.0,
                            "",
                            emptyList(),
                            100,
                            0.0,
                            0.0,
                            "",
                            "Big and hard to cook recipe",
                            "https://koelov.ru/wp-content/uploads/2013/10/kabachkovyj-tort-recept-prigotovleniya.jpg"
                        )
                    )
                )
            }
        }
    }
}

private val testList = listOf(
    Image("https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg", "1"),
    Image(
        "https://www.journaldev.com/wp-content/uploads/2018/06/android-instant-app-module-dependencies.png.webp",
        "2"
    ),
    Image(
        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
        "3"
    ),
    Image("https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg", "4"),
    Image(
        "https://www.journaldev.com/wp-content/uploads/2018/06/android-instant-app-module-dependencies.png.webp",
        "5"
    )
)