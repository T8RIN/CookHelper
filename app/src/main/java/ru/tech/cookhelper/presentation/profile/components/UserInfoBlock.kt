package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.getLastAvatar
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.UserState
import ru.tech.cookhelper.presentation.ui.theme.Gray
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserInfoBlock(
    userState: UserState,
    onEdit: () -> Unit,
    onAvatarClick: (/*avatarList: List<FileData?>*/) -> Unit,
    onStatusUpdate: (currentStatus: String) -> Unit
) {
    val lastSeen by remember(userState) {
        derivedStateOf {
            val lastSeen = userState.user?.lastSeen ?: 0L
            val df = if (Calendar.getInstance()[Calendar.YEAR] != SimpleDateFormat(
                    "yyyy",
                    Locale.getDefault()
                ).format(lastSeen).toInt()
            ) {
                SimpleDateFormat("d MMMM yyyy HH:mm", Locale.getDefault())
            } else SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())
            df.format(lastSeen)
        }
    }

    Column(Modifier.padding(horizontal = 15.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Picture(
                model = userState.user?.getLastAvatar(),
                modifier = Modifier
                    .padding(top = 15.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarClick(/*TODO: Provide avatar list from user */) },
                error = {
                    Icon(Icons.Filled.AccountCircle, null)
                }
            )
            Spacer(Modifier.width(10.dp))
            Column(
                Modifier.padding(top = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = userState.user?.let { "${it.name} ${it.surname}" }.toString(),
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(450)
                )
                Spacer(Modifier.height(5.dp))
                if (userState.user?.status?.isEmpty() == true) {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(
                                onClick = { onStatusUpdate(userState.user.status) }
                            )
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
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(
                                onClick = { onStatusUpdate(userState.user?.status ?: "") }
                            )
                    ) {
                        Spacer(Modifier.width(5.dp))
                        Text(
                            userState.user?.status.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.width(5.dp))
                    }
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
                        tint = Gray
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = lastSeen,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray
                    )
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onEdit
        ) {
            Text(stringResource(R.string.edit))
        }
    }
}