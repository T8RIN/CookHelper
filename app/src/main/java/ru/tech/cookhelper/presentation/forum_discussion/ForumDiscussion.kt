package ru.tech.cookhelper.presentation.forum_discussion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.PlaylistAdd
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.core.constants.Constants.LOREM_IPSUM
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.forum_discussion.components.ChipGroup
import ru.tech.cookhelper.presentation.forum_discussion.components.RatingButton
import ru.tech.cookhelper.presentation.profile.components.AuthorBubble
import ru.tech.cookhelper.presentation.profile.components.PostActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.compose.squareSize
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumDiscussion(id: Int, title: String, onBack: () -> Boolean) {
    val scrollBehavior = topAppBarScrollBehavior()
    val screenController = LocalScreenController.current
    Column {
        TopAppBar(
            title = { Text(title, fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            },
            scrollBehavior = scrollBehavior
        )
        LazyColumn(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            item {
                Spacer(Modifier.size(16.dp))
                AuthorBubble(
                    author = User(
                        id = 1,
                        avatar = listOf("https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album"),
                        name = "Малик",
                        surname = "Мухаметзянов",
                        token = "",
                        email = "",
                        nickname = "t8rin",
                        verified = true,
                        lastSeen = 0L
                    ),
                    modifier = Modifier.padding(start = 20.dp),
                    timestamp = "today 21:20",
                    onClick = {

                    }
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "How to cook an dinner?",
                    Modifier.padding(horizontal = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.size(5.dp))
                Text(text = LOREM_IPSUM, Modifier.padding(horizontal = 20.dp), fontSize = 16.sp)
                Spacer(Modifier.size(10.dp))
                Box(Modifier.fillMaxWidth()) {
                    Picture(
                        model = "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2022-03/plant-based-food-mc-220323-02-273c7b.jpg",
                        modifier = Modifier
                            .squareSize()
                            .align(Alignment.Center)
                            .clickable {
                                screenController.navigate(
                                    Screen.FullscreenImagePager(
                                        "id",
                                        emptyList()
                                    )
                                )
                            },
                        shape = RoundedCornerShape(4.dp)
                    )
                }
                Spacer(Modifier.size(4.dp))
                ChipGroup(
                    modifier = Modifier.padding(8.dp),
                    chips = listOf(
                        "Forum",
                        "rice",
                        "topic",
                        "big and hard to answer",
                        "my",
                        "CookHelper"
                    ).sortedBy { it.length }
                ) {
                    //TODO: Open Page with search by this tag
                }
                Spacer(Modifier.size(4.dp))
                Separator(Modifier.padding(horizontal = 6.dp))
                Spacer(Modifier.size(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingButton(
                        userRate = 1,
                        currentRating = "+9292",
                        modifier = Modifier.height(32.dp),
                        onRateUp = {},
                        onRateDown = {}
                    )
                    Spacer(Modifier.width(8.dp))
                    PostActionButton(
                        onClick = { /*TODO*/ },
                        icon = Icons.Rounded.ChatBubbleOutline,
                        text = "253",
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                    )
                    Spacer(Modifier.width(8.dp))
                    PostActionButton(
                        onClick = { /*TODO*/ },
                        icon = Icons.Rounded.PlaylistAdd,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                    )
                    Spacer(Modifier.width(8.dp))
                    PostActionButton(
                        onClick = { /*TODO*/ },
                        icon = Icons.Rounded.Share,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                    )
                }

            }
        }
    }
}