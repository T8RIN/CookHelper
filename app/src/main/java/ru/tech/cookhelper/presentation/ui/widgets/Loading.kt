package ru.tech.cookhelper.presentation.ui.widgets

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.ui.theme.DialogShape

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(if (modifier == Modifier) Modifier.fillMaxSize() else modifier) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun LoadingDialog(
    visible: Boolean = true,
    isCancelable: Boolean = false,
    onDismiss: () -> Unit = {}
) {
    var wantToDismiss by rememberSaveable { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        Dialog(
            onDismissRequest = { if (isCancelable) wantToDismiss = true },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            enabled = true
                        ) {
                            if (isCancelable) wantToDismiss = true
                        }
                )
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        shape = DialogShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(24.dp))
                }
            }

            AnimatedVisibility(
                visible = wantToDismiss,
                enter = fadeIn(tween(200)),
                exit = fadeOut(tween(200)),
            ) {
                BackHandler { if (isCancelable) wantToDismiss = false }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures {
                                if (isCancelable) wantToDismiss = false
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(vertical = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                shape = DialogShape
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(8.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.loading),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.loading_description),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(Modifier.height(16.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                TextButton(onClick = onDismiss) {
                                    Text(stringResource(R.string.interrupt))
                                }
                                Spacer(Modifier.width(8.dp))
                                TextButton(onClick = { wantToDismiss = false }) {
                                    Text(stringResource(R.string.wait))
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }

}