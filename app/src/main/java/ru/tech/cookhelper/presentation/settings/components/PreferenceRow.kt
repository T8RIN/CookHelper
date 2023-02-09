package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferenceRow(
    modifier: Modifier = Modifier,
    title: String,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = { },
    onLongClick: () -> Unit = { },
    subtitle: String? = null,
    action: @Composable (() -> Unit)? = null
) {
    val height = if (subtitle != null) 72.dp else 56.dp

    val titleStyle = MaterialTheme.typography.bodyLarge
    val subtitleTextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
    )

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = onClick
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = height),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Spacer(Modifier.width(20.dp))
                icon()
            }
            Text(
                text = title,
                style = titleStyle,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = if (icon == null) 20.dp else 16.dp)
            )
            if (action != null) {
                Box(
                    Modifier
                        .widthIn(min = 56.dp)
                        .padding(end = 20.dp),
                ) {
                    action()
                }
            }
        }
        if (subtitle != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = subtitleTextStyle,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun PreferenceRow(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    onClick: () -> Unit = { },
    onLongClick: () -> Unit = { },
    subtitle: String? = null,
    action: @Composable (() -> Unit)? = null
) {
    val privateIcon: (@Composable () -> Unit)? = if (icon != null) {
        {
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = SquircleShape(14.dp),
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(42.dp)
            ) {
                Box(Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    } else null

    PreferenceRow(
        title = title,
        modifier = modifier,
        icon = privateIcon,
        onClick = onClick,
        onLongClick = onLongClick,
        subtitle = subtitle,
        action = action
    )
}

@Composable
fun PreferenceRowSwitch(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    onClick: () -> Unit = { },
    subtitle: String? = null,
    checked: Boolean
) {
    val thumbIcon: (@Composable () -> Unit)? = if (checked) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize)
            )
        }
    } else null

    PreferenceRow(
        modifier = modifier,
        title = title,
        icon = icon,
        onClick = onClick,
        subtitle = subtitle,
        action = {
            Switch(
                thumbContent = thumbIcon,
                checked = checked,
                onCheckedChange = null
            )
        },
    )
}