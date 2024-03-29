package ru.tech.cookhelper.presentation.settings.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppThemePreviewItem(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.CheckCircle,
    selected: Boolean,
    colorScheme: ColorScheme,
    shapes: Shapes,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f / 1.7f)
            .border(
                width = 4.dp,
                color = if (selected) {
                    colorScheme.primary
                } else {
                    colorScheme.outlineVariant
                },
                shape = RoundedCornerShape(16.dp),
            )
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.background)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = selected,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorScheme.primary
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(2.dp, shapes.small)
                .background(
                    color = colorScheme.surfaceVariant,
                    shape = shapes.small,
                )
                .fillMaxWidth(1f),
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .height(36.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .weight(1f)
                        .shadow(2.dp, RoundedCornerShape(5.5.dp))
                        .background(
                            color = colorScheme.tertiary,
                            shape = RoundedCornerShape(5.5.dp)
                        ),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .weight(1f)
                        .shadow(2.dp, RoundedCornerShape(5.5.dp))
                        .background(
                            color = colorScheme.secondary,
                            shape = RoundedCornerShape(5.5.dp)
                        )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .shadow(2.dp, shapes.small)
                    .background(
                        color = colorScheme.primary,
                        shape = shapes.small,
                    )
            )
        }

        // Bottom bar
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .background(colorScheme.surfaceColorAtElevation(3.dp))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .weight(1f)
                        .shadow(2.dp, shapes.small)
                        .background(
                            color = colorScheme.secondaryContainer,
                            shape = shapes.small,
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(18.dp)
                        .shadow(2.dp, RoundedCornerShape(6.dp))
                        .background(
                            color = colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp),
                        )
                )
            }
        }
    }
}