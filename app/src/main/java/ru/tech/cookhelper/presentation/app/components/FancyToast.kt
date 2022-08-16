package ru.tech.cookhelper.presentation.app.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun FancyToast(fancyToastValues: FancyToastValues) {
    val showToast = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }
    val conf = LocalConfiguration.current
    val sizeMin = min(conf.screenWidthDp, conf.screenHeightDp).dp
    val sizeMax = max(conf.screenWidthDp, conf.screenHeightDp).dp

    fancyToastValues.apply {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = sizeMax * 0.2f)
                    .then(
                        if (showToast.isIdle) Modifier.shadow(4.dp, RoundedCornerShape(24.dp))
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
                    shape = RoundedCornerShape(24.dp),
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
                delay(if (length == Toast.LENGTH_LONG) 5000L else 2500L)
                showToast.targetState = false
            }
        }
    }
}

fun MutableState<FancyToastValues>.sendToast(
    icon: ImageVector?,
    message: String,
    length: Int = Toast.LENGTH_LONG
) {
    var fancyToastValues = FancyToastValues(icon, message, length)
    if (value == fancyToastValues) fancyToastValues = value.copy(message = value.message + " ")

    CoroutineScope(Dispatchers.Main).launch {
        delay(200L)
        this@sendToast.apply {
            value = value.copy(message = message, icon = icon)
        }
    }
    value = fancyToastValues
}

data class FancyToastValues(
    val icon: ImageVector? = null,
    val message: String = "",
    val length: Int = Toast.LENGTH_LONG
)