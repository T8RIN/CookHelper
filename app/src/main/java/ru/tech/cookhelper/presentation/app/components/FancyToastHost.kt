package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import kotlin.math.min

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun FancyToastHost(fancyToastValues: FancyToastValues) {
    val showToast = remember { MutableTransitionState(false) }
    val conf = LocalConfiguration.current
    val sizeMin = min(conf.screenWidthDp, conf.screenHeightDp).dp
    val scale by animateFloatAsState(targetValue = if (showToast.targetState) 1f else 0f)
    val alpha by animateFloatAsState(targetValue = if (showToast.targetState) 0.95f else 0f)

    fancyToastValues.apply {
        AnimatedVisibility(
            visibleState = showToast,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
            ) {
                Card(
                    colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .heightIn(min = 48.dp)
                        .widthIn(min = 0.dp, max = (sizeMin * 0.7f))
                        .padding(
                            bottom = sizeMin * 0.2f,
                            top = 24.dp,
                            start = 12.dp,
                            end = 12.dp
                        )
                        .scale(scale)
                        .shadow(4.dp, SquircleShape(24.dp))
                        .alpha(alpha),
                    shape = SquircleShape(24.dp)
                ) {
                    Row(
                        Modifier.padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        icon?.let { Icon(it, null) }
                        if (message != "") {
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                style = MaterialTheme.typography.bodySmall,
                                text = message,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                        }
                    }
                }
            }
        }
        LaunchedEffect(fancyToastValues) {
            if (message != "") {
                if (showToast.currentState) {
                    showToast.targetState = false
                    delay(200L)
                }
                showToast.targetState = true
                delay(length)
                showToast.targetState = false
            }
        }
    }
}

fun MutableState<FancyToastValues>.sendToast(
    icon: ImageVector?,
    message: String,
    length: Long = Toast.Long.time
) = CoroutineScope(Dispatchers.Main).launch {
    val fancyToastValues = FancyToastValues(icon, message, length)
    value = if (value == fancyToastValues) {
        fancyToastValues.copy(message = " ${value.message} ")
    } else fancyToastValues
    cancel()
}

data class FancyToastValues(
    val icon: ImageVector? = null,
    val message: String = "",
    val length: Long = Toast.Long.time
)

sealed class Toast(val time: kotlin.Long) {
    object Long : Toast(3500L)
    object Short : Toast(1500L)
}

@Composable
fun rememberFancyToastValues() = remember { mutableStateOf(FancyToastValues()) }