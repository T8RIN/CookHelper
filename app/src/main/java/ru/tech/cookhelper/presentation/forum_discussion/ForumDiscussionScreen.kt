package ru.tech.cookhelper.presentation.forum_discussion

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.constants.Constants.LOREM_IPSUM
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Reply
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.presentation.app.components.CozyTextField
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.Placeholder
import ru.tech.cookhelper.presentation.app.components.TextFieldAppearance
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.forum_discussion.components.TagGroup
import ru.tech.cookhelper.presentation.forum_discussion.components.ExpandableFloatingActionButtonWithExtra
import ru.tech.cookhelper.presentation.forum_discussion.components.ForumReplyItem
import ru.tech.cookhelper.presentation.forum_discussion.components.RatingButton
import ru.tech.cookhelper.presentation.profile.components.AuthorBubble
import ru.tech.cookhelper.presentation.profile.components.PostActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.ui.theme.ForumRemove
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.blur
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.harmonizeWithPrimary
import ru.tech.cookhelper.presentation.ui.utils.compose.PaddingUtils.addPadding
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.pluralStringResource
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isLastItemVisible
import ru.tech.cookhelper.presentation.ui.utils.compose.ScrollUtils.isScrollingUp
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.compose.navigationBarsLandscapePadding
import ru.tech.cookhelper.presentation.ui.utils.compose.squareSize
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import kotlin.math.min


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ForumDiscussionScreen(id: Int, title: String, onBack: () -> Unit) {
    BackHandler(onBack = onBack)

    val scrollBehavior = topAppBarScrollBehavior()
    val screenController = LocalScreenController.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val textStyle = LocalTextStyle.current
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var job by remember { mutableStateOf<Job?>(null) }
    DisposableEffect(Unit) {
        onDispose { job?.cancel() }
    }
    val placeholderSize = configuration.minScreenDp or 460.dp

    var applyBottomPadding by remember { mutableStateOf<Job?>(null) }
    var bottomPadding by remember { mutableStateOf(48.dp) }
    val isAtTheBottom = lazyListState.isLastItemVisible()

    //TODO: Create Picture.kt with blurred background

    val user = User(
        id = 1,
        avatar = listOf(
            FileData(
                "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                ""
            )
        ),
        fridge = emptyList(),
        name = "Малик",
        surname = "Мухаметзянов",
        token = "",
        email = "",
        nickname = "t8rin",
        verified = true,
        lastSeen = 0L
    )


    val answerList: List<Reply>? = listOf(
        Reply(
            user,
            System.currentTimeMillis(),
            emptyList(),
            "Lorem ipsum dolor sit amet",
            1,
            0,
            emptyList(),
            emptyList(),
            listOf(
                Reply(
                    user, System.currentTimeMillis(), listOf(
                        FileData(
                            "https://sun1-89.userapi.com/impf/zNPPyzy-fIkM0yKJRQxrgTXvs0GRq8o3r3R2cg/FzpwGJudQi4.jpg?size=1461x2160&quality=95&sign=16250424fdef8401465f946368bc8188&type=album",
                            "1"
                        )
                    ), "Lorem ipsum dolor sit amet", 2, 0, emptyList(), emptyList(),
                    listOf(
                        Reply(
                            user,
                            System.currentTimeMillis(),
                            emptyList(),
                            "Lorem ipsum dolor sit amet",
                            3,
                            0,
                            emptyList(),
                            emptyList(),
                            listOf(
                                Reply(
                                    user,
                                    System.currentTimeMillis(),
                                    emptyList(),
                                    "Lorem ipsum dolor sit amet",
                                    4,
                                    0,
                                    emptyList(),
                                    emptyList(),
                                    emptyList()
                                ),
                                Reply(
                                    user,
                                    System.currentTimeMillis(),
                                    emptyList(),
                                    "Lorem ipsum dolor sit amet",
                                    5,
                                    0,
                                    emptyList(),
                                    emptyList(),
                                    emptyList()
                                )
                            )
                        )
                    )
                )
            )
        ),
        Reply(
            user,
            System.currentTimeMillis(),
            emptyList(),
            "Lorem ipsum dolor sit amet",
            6,
            0,
            emptyList(),
            emptyList(),
            listOf(
                Reply(
                    user,
                    System.currentTimeMillis(),
                    emptyList(),
                    "Lorem ipsum dolor sit amet",
                    7,
                    0,
                    emptyList(),
                    emptyList(),
                    emptyList()
                ),
                Reply(
                    user,
                    System.currentTimeMillis(),
                    emptyList(),
                    "Lorem ipsum dolor sit amet",
                    8,
                    0,
                    emptyList(),
                    emptyList(),
                    emptyList()
                ),
                Reply(
                    user,
                    System.currentTimeMillis(),
                    emptyList(),
                    "Lorem ipsum dolor sit amet",
                    9,
                    0,
                    emptyList(),
                    emptyList(),
                    emptyList()
                ),
            )
        ),
    )
    val answerCount = 9


    Box(Modifier.fillMaxSize()) {
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
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
                    .addPadding(bottom = 32.dp + bottomPadding)
            ) {
                item {
                    Spacer(Modifier.size(16.dp))
                    AuthorBubble(
                        author = user,
                        modifier = Modifier.padding(start = 20.dp),
                        timestamp = "today 21:20",
                        onClick = {

                        }
                    )
                    Spacer(Modifier.size(16.dp))
                    Text(text = LOREM_IPSUM, Modifier.padding(horizontal = 20.dp), fontSize = 16.sp)
                    Spacer(Modifier.size(10.dp))
                    Box(Modifier.fillMaxWidth()) {
                        Picture(
                            model = bitmap,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(configuration.minScreenDp),
                            shape = RectangleShape
                        )
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
                            allowHardware = false,
                            onSuccess = {
                                job?.cancel()
                                if (configuration.isLandscape) {
                                    job = scope.launch {
                                        delay(300)
                                        bitmap =
                                            it.result.drawable.toBitmap().blur(scale = 0.5f, 60)
                                    }
                                }
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                    }
                    Spacer(Modifier.size(4.dp))
                    TagGroup(
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
                        Spacer(Modifier.weight(1f))
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
                    Spacer(Modifier.size(4.dp))
                    Separator(Modifier.padding(horizontal = 6.dp))
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.answers,
                            count = answerCount,
                            onZero = { stringResource(R.string.no_answers) }
                        ),
                        Modifier.padding(horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.size(16.dp))
                    if (answerCount == 0) {
                        Placeholder(
                            icon = Icons.Outlined.ForumRemove,
                            text = stringResource(R.string.no_answers_placeholder),
                            modifier = Modifier
                                .height(placeholderSize)
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .navigationBarsLandscapePadding()
                        )
                    }
                }
                answerList?.let {
                    items(answerList, key = { it.id }) {
                        var hideReplies by rememberSaveable { mutableStateOf(true) }
                        ForumReplyItem(
                            reply = it,
                            modifier = Modifier.padding(end = 12.dp, start = 4.dp),
                            showDivider = false,
                            isFirst = true,
                            hideReplies = hideReplies,
                            onShowReplies = { hideReplies = false },
                            onAuthorClick = {

                            }
                        )
                    }
                }
            }
        }

        var isExtra by remember { mutableStateOf(false) }

        ExpandableFloatingActionButtonWithExtra(
            isExtra = isExtra,
            expanded = lazyListState.isScrollingUp(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .navigationBarsPadding()
                .imePadding()
                .onSizeChanged {
                    applyBottomPadding?.cancel()
                    applyBottomPadding = scope.launch {
                        if (isExtra || lazyListState.isScrollInProgress) delay(150)
                        if (isAtTheBottom) bottomPadding = with(density) { it.height.toDp() }
                    }
                },
            icon = { Icon(Icons.Rounded.Reply, null, modifier = Modifier.size(it)) },
            text = { Text(stringResource(R.string.reply)) },
            expandedContent = {
                val colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
                var commentText by rememberSaveable { mutableStateOf("") }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(onClick = { /*TODO*/ }, colors = colors) {
                        Icon(Icons.Rounded.AttachFile, null)
                    }
                    Spacer(Modifier.width(8.dp))
                    CozyTextField(
                        value = commentText,
                        appearance = TextFieldAppearance.Outlined,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer.harmonizeWithPrimary(
                                0.7f
                            ),
                            unfocusedLabelColor = MaterialTheme.colorScheme.secondaryContainer.harmonizeWithPrimary(
                                0.7f
                            ),
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        shape = SquircleShape(8.dp),
                        onValueChange = { commentText = it.trim() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp),
                        textStyle = textStyle,
                        maxLines = 5,
                        label = stringResource(R.string.comment)
                    )
                    Spacer(Modifier.width(8.dp))
                    FilledIconButton(onClick = { /*TODO*/ }, colors = colors) {
                        Icon(Icons.Rounded.Send, null)
                    }
                }
                BackHandler { isExtra = false }
            },
            transitionSpec = {
                if (targetState > initialState) {
                    fadeIn() with fadeOut()
                } else {
                    slideInHorizontally { -it / 2 } + fadeIn() with
                            slideOutHorizontally { it / 2 } + fadeOut()
                }
            },
            onClick = { isExtra = !isExtra }
        )
    }
}

private infix fun Dp.or(dp: Dp): Dp {
    return if (this >= dp) dp
    else this
}

private val Configuration.isLandscape: Boolean
    get() {
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }

private val Configuration.minScreenDp: Dp
    get() {
        return min(screenHeightDp, screenWidthDp).dp
    }