package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.recipe_post_creation.components.ExpandableFloatingActionButton
import ru.tech.cookhelper.presentation.recipe_post_creation.components.FabSize

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
    optionsGravity: OptionsGravity = OptionsGravity.End,
    onOptionSelected: (option: FabOption) -> Unit,
    onClick: () -> Unit = {}
) {
    val horizontalAlignment = when (optionsGravity) {
        OptionsGravity.Start -> Alignment.Start
        OptionsGravity.Center -> Alignment.CenterHorizontally
        OptionsGravity.End -> Alignment.End
    }
    val crossAxisAlignment = when (optionsGravity) {
        OptionsGravity.Start -> FlowCrossAxisAlignment.Start
        OptionsGravity.Center -> FlowCrossAxisAlignment.Center
        OptionsGravity.End -> FlowCrossAxisAlignment.End
    }
    var showingOptions by rememberSaveable { mutableStateOf(false) }
    Scrim(
        showing = showingOptions,
        onTap = { showingOptions = false },
        color = scrimColor
    )
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        AnimatedVisibility(visible = showingOptions, modifier = Modifier.weight(1f, false)) {
            FlowColumn(
                crossAxisAlignment = crossAxisAlignment,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)
            ) {
                options.forEachIndexed { index, option ->
                    ExpandableFloatingActionButton(
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
                                Text(option.text)
                            }
                        },
                        expanded = option.text.isNotEmpty(),
                        onClick = {
                            onOptionSelected(option.copy(index = index))
                            showingOptions = false
                        }
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
        ExpandableFloatingActionButton(
            onClick = {
                onClick()
                showingOptions = !showingOptions
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
                AnimatedContent(targetState = showingOptions) { showClose ->
                    if (showClose) Text(stringResource(R.string.close))
                    else text()
                }
            }
        )
    }
}

data class FabOption(
    val index: Int = 0,
    val text: String = "",
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
            Modifier
                .fillMaxSize()
                .pointerInput(onTap) {
                    detectTapGestures { onTap() }
                }
        ) {
            drawRect(color, alpha = fraction())
        }
    }
}