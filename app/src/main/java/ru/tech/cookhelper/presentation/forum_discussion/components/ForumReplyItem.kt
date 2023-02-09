package ru.tech.cookhelper.presentation.forum_discussion.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Reply
import ru.tech.cookhelper.domain.model.getRating
import ru.tech.cookhelper.domain.model.userRate
import ru.tech.cookhelper.presentation.profile.components.AuthorBubble
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForumReplyItem(
    reply: Reply,
    modifier: Modifier = Modifier,
    onAuthorClick: (authorId: Long) -> Unit,
    hideReplies: Boolean = false,
    onShowReplies: () -> Unit = {},
    isFirst: Boolean = false,
    showDivider: Boolean = true
) {
    val density = LocalDensity.current
    var height by remember { mutableStateOf(0.dp) }

    Row(
        Modifier
            .fillMaxWidth()
            .then(modifier)
            .animateContentSize(),
        horizontalArrangement = Arrangement.Start
    ) {
        if (showDivider) {
            Box(
                Modifier
                    .width(DividerDefaults.Thickness)
                    .height(height)
                    .background(color = MaterialTheme.colorScheme.outlineVariant)
            )
        }
        Column(
            Modifier
                .padding(start = 8.dp)
                .onGloballyPositioned {
                    height = with(density) { it.size.height.toDp() }
                }
        ) {
            val timestamp by remember {
                derivedStateOf {
                    val df =
                        if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat(
                                "yyyy",
                                Locale.getDefault()
                            )
                                .format(reply.timestamp)
                                .toInt()
                        ) {
                            SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
                        } else SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
                    df.format(reply.timestamp)
                }
            }
            if (isFirst) Spacer(Modifier.height(16.dp))
            AuthorBubble(
                author = reply.author,
                timestamp = timestamp,
                onClick = { onAuthorClick(reply.author.id) },
                pictureModifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(reply.text, Modifier.padding(start = 4.dp), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))

            reply.attachments.takeIf { it.isNotEmpty() }?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Picture(
                    model = it.firstOrNull()?.link,
                    modifier = Modifier
                        .clickable {
//                        screenController.navigate(
//                            Screen.FullscreenImagePager(
//                                "id",
//                                emptyList()
//                            )
//                        )
                        },
                    shape = RoundedCornerShape(4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(Modifier.fillMaxSize()) {
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text(
                        stringResource(R.string.reply),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
                Spacer(Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                RatingButton(
                    userRate = reply.userRate(reply.author),
                    currentRating = reply.getRating(),
                    modifier = Modifier.height(32.dp),
                    onRateUp = {},
                    onRateDown = {}
                )
            }

            if (!hideReplies) {
                reply.replies.forEach {
                    ForumReplyItem(reply = it, onAuthorClick = onAuthorClick)
                }
            } else if (reply.replies.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                FilledTonalButton(onClick = { onShowReplies() }, Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.open_replies, reply.replies.size))
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
