package ru.tech.cookhelper.presentation.app.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.BorderStroke
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
fun FancyToast(
    icon: ImageVector,
    message: String = "",
    changed: MutableState<Boolean>,
    length: Int = Toast.LENGTH_LONG
) {
    val showToast = remember {
        MutableTransitionState(false).apply {
            targetState = false
        }
    }
    val conf = LocalConfiguration.current
    val sizeMin = min(conf.screenWidthDp, conf.screenHeightDp).dp
    val sizeMax = max(conf.screenWidthDp, conf.screenHeightDp).dp

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = sizeMax * 0.2f),
            visibleState = showToast,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Card(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiaryContainer),
                colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                modifier = Modifier
                    .alpha(0.98f)
                    .heightIn(48.dp)
                    .widthIn(0.dp, (sizeMin * 0.7f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(icon, null)
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

    LaunchedEffect(changed.value) {
        changed.value = false
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

fun FancyToastValues.sendToast(icon: ImageVector, text: String, length: Int = Toast.LENGTH_LONG) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(200L)
        this@sendToast.text?.value = text
        this@sendToast.icon?.value = icon
    }
    this.text?.value = " ${this.text?.value} "
    this.length?.value = length
    this.changed?.value = true
}

operator fun String.times(size: Int): String {
    var s = ""
    for (i in 0 until size) s += this
    return s
}

data class FancyToastValues(
    val icon: MutableState<ImageVector>? = null,
    val text: MutableState<String>? = null,
    val changed: MutableState<Boolean>? = null,
    val length: MutableState<Int>? = null
)