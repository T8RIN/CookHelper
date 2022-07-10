package ru.tech.cookhelper.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.domain.model.Post
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.profile.components.*
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.ui.utils.Screen
import ru.tech.cookhelper.presentation.ui.utils.name
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.currentScreen
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = scopedViewModel(),
    updateTitle: (title: String) -> Unit
) {
    val screenController = LocalScreenController.current

    val userState = viewModel.userState.value
    val nick = userState.user?.nickname
    if (nick != null) LaunchedEffect(Unit) { updateTitle(nick) }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    val status = userState.user?.status
    val lastSeen by derivedStateOf {
        val lastSeen = userState.user?.lastSeen ?: 0L
        val df =
            if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat("yyyy").format(lastSeen)
                    .toInt()
            ) {
                SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
            } else {
                SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
            }
        df.format(lastSeen)
    }

    Spacer(Modifier.width(15.dp))

    LazyColumn(state = rememberForeverLazyListState(key = Screen.Profile::class.name)) {
        item {
            Column {
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

                Spacer(Modifier.height(20.dp))
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
                Spacer(Modifier.height(30.dp))
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
                            modifier = Modifier.weight(2f),
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

                Spacer(Modifier.height(10.dp))
                val tabs =
                    listOf(stringResource(R.string.all_posts), stringResource(R.string.recipes))
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
                Spacer(Modifier.height(30.dp))
            }
        }
        if (selectedTabIndex.toTab() == SelectedTab.Posts) {
            items(
                listOf(
                    Post(
                        "1",
                        "2",
                        timestamp = System.currentTimeMillis(),
                        title = "Мем года",
                        liked = false,
                        text = "Вампиры суициндики долбят чеснок",
                        likeCount = Random.nextInt(100, 1000),
                        commentsCount = Random.nextInt(100, 200),
                        image = null
                    ),
                    Post(
                        "2",
                        "2",
                        timestamp = System.currentTimeMillis(),
                        title = "Мем года",
                        liked = true,
                        text = "Вампиры суициндики долбят чеснок",
                        likeCount = Random.nextInt(100, 1000),
                        commentsCount = Random.nextInt(100, 200),
                        image = Image(
                            "https://sun1-19.userapi.com/impg/0_BW3-31ZjfwZyHV0B0-YfVUjWiBOFL2SpH0HQ/kKuUGarykVA.jpg?size=2160x1440&quality=96&sign=7843f461f7570a9b792b3bdaea627753&type=album",
                            "7"
                        )
                    ),
                    Post(
                        "2",
                        "2",
                        timestamp = System.currentTimeMillis(),
                        title = null,
                        liked = true,
                        text = "Вампиры суициндики долбят чеснок",
                        likeCount = Random.nextInt(100, 1000),
                        commentsCount = Random.nextInt(100, 200),
                        image = Image(
                            "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                            "7"
                        )
                    )
                )
            ) { post ->
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
                    onImageClick = {
                        screenController.navigate(
                            Screen.FullscreenImage(
                                id = it,
                                previousScreen = Screen.Profile
                            )
                        )
                    },
                    onAuthorClick = {

                    },
                    onPostClick = {

                    },
                    onLikeClick = {

                    }
                )
                Divider(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    thickness = 8.dp
                )
            }
        }
    }
}

private val testList = listOf(
    Image(
        "https://sun9-76.userapi.com/s/v1/ig2/lbF4vZbkOi0zdhtU-5iXrF2YPHiwIVVZouCCGvQBb7MV7OKzhhPUg7KW4nyc7vr7SS7HVDDyV_kdPVeoPo7j8RHb.jpg?size=1620x2160&quality=95&type=album",
        "3"
    ),
    Image(
        "https://sun1-19.userapi.com/impg/0_BW3-31ZjfwZyHV0B0-YfVUjWiBOFL2SpH0HQ/kKuUGarykVA.jpg?size=2160x1440&quality=96&sign=7843f461f7570a9b792b3bdaea627753&type=album",
        "4"
    ),
    Image(
        "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
        "5"
    ),
    Image("https://ciroccodentalcenterpa.com/wp-content/uploads/foods-fight-plaque.jpg", "1"),
    Image(
        "https://www.journaldev.com/wp-content/uploads/2018/06/android-instant-app-module-dependencies.png.webp",
        "2"
    )
)