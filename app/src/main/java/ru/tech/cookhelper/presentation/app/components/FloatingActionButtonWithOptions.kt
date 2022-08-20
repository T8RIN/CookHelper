package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingActionButtonWithOptions(
    modifier: Modifier = Modifier,
    icon: @Composable (iconSize: Dp) -> Unit,
    text: @Composable () -> Unit = {},
    expanded: Boolean = false,
    options: List<FabOption>,
    scrimEnabled: Boolean = true,
    scrimColor: Color = MaterialTheme.colorScheme.scrim.copy(0.32f),
    globalOptionsGravity: OptionsGravity = OptionsGravity.End,
    internalOptionsGravity: OptionsGravity = OptionsGravity.End,
    onOptionSelected: (option: FabOption) -> Unit,
    onClick: (showingOptions: Boolean) -> Unit = {}
) {
    var showingOptions by rememberSaveable { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (showingOptions) 1f else 0f)

    val horizontalAlignment = when (globalOptionsGravity) {
        OptionsGravity.Start -> Alignment.Start
        OptionsGravity.Center -> Alignment.CenterHorizontally
        OptionsGravity.End -> Alignment.End
    }
    val crossAxisAlignment = when (internalOptionsGravity) {
        OptionsGravity.Start -> FlowCrossAxisAlignment.Start
        OptionsGravity.Center -> FlowCrossAxisAlignment.Center
        OptionsGravity.End -> FlowCrossAxisAlignment.End
    }
    Scrim(
        showing = showingOptions,
        onTap = { showingOptions = false },
        color = scrimColor
    )
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        AnimatedVisibility(
            visible = showingOptions,
            modifier = Modifier.weight(1f, false),
            enter = fadeIn() + slideInVertically { it / 3 },
            exit = fadeOut() + slideOutVertically { it / 3 }
        ) {
            FlowColumn(
                crossAxisAlignment = crossAxisAlignment,
                crossAxisSpacing = 4.dp,
                mainAxisSpacing = 2.dp,
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                )
            ) {
                options.forEachIndexed { index, option ->
                    ExpandableFloatingActionButton(
                        modifier = Modifier.scale(scale),
                        size = FabSize.Small,
                        icon = {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = null,
                                modifier = Modifier.size(it)
                            )
                        },
                        text = {
                            if (option.text.isNotEmpty()) {
                                Text(text = option.text.asString())
                            }
                        },
                        expanded = option.text.isNotEmpty(),
                        onClick = {
                            onOptionSelected(option.copy(index = index))
                            showingOptions = false
                        }
                    )
                }
            }
        }
        ExpandableFloatingActionButton(
            onClick = {
                showingOptions = !showingOptions
                onClick(showingOptions)
            },
            expanded = expanded,
            icon = { size ->
                AnimatedContent(targetState = showingOptions) { showClose ->
                    if (showClose) Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier.size(size)
                    )
                    else icon(size)
                }
            },
            text = {
                if (text != {}) {
                    AnimatedContent(targetState = showingOptions) { showClose ->
                        if (showClose) Text(stringResource(R.string.close))
                        else text()
                    }
                }
            }
        )
    }
}

data class FabOption(
    val index: Int = 0,
    val text: UIText = UIText.Empty(),
    val icon: ImageVector
)

enum class OptionsGravity { Start, Center, End }

@Composable
fun Scrim(
    showing: Boolean,
    onTap: () -> Unit,
    fraction: () -> Float = { 1f },
    color: Color = MaterialTheme.colorScheme.scrim.copy(0.32f)
) {
    AnimatedVisibility(
        visible = showing,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onTap()
                }
        ) {
            drawRect(color = color, alpha = fraction())
        }
    }
}