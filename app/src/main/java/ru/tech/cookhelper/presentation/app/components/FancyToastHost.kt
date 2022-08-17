package ru.tech.cookhelper.presentation.app.components

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

    fancyToastValues.apply {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = sizeMin * 0.2f, top = 24.dp)
                    .then(
                        if (showToast.isIdle) Modifier.shadow(4.dp, SquircleShape(24.dp))
                        else Modifier
                    ),
                visibleState = showToast,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Card(
                    colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                    modifier = Modifier
                        .alpha(0.98f)
                        .heightIn(48.dp)
                        .widthIn(0.dp, (sizeMin * 0.7f)),
                    shape = SquircleShape(24.dp),
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
) {
    CoroutineScope(Dispatchers.Main).launch {
        val fancyToastValues = FancyToastValues(icon, message, length)
        delay(200L)
        value = if (value == fancyToastValues) {
            fancyToastValues.copy(message = " ${value.message} ")
        } else fancyToastValues
        cancel()
    }
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