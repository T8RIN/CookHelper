package ru.tech.cookhelper.presentation.authentication.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneOutline
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.authentication.viewModel.AuthViewModel
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost
import java.util.*

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ConfirmEmailField(mod: Float, viewModel: AuthViewModel) {

    val toastHost = LocalToastHost.current
    val width = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current

    val size = when {
        width >= 16 * 5 + 50 * 6 -> 50
        else -> ((width - 16 * 5) / 6)
    }

    Text(
        stringResource(R.string.last_step,
            viewModel.currentName.replaceFirstChar { it.titlecase(Locale.getDefault()) }),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(8.dp * mod))
    Text(
        stringResource(R.string.code_sent_to_your_email, viewModel.currentEmail),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.size(64.dp * mod))

    OTPField(length = 6, codeState = viewModel.codeState.value, onFilled = {
        viewModel.checkVerificationCode(it)
    })

    Spacer(Modifier.size(16.dp * mod))
    Row(
        modifier = Modifier.defaultMinSize(
            minWidth = (size * 6 + 16 * 3).dp
        ),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            enabled = viewModel.codeTimeout == 0,
            onClick = { viewModel.askForVerificationCode() },
            content = {
                Text(
                    if (viewModel.codeTimeout != 0) stringResource(
                        R.string.get_code_again,
                        viewModel.codeTimeout
                    ) else stringResource(R.string.get_code)
                )
            }
        )
    }
    Spacer(Modifier.size(48.dp * mod))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.mistaken),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { viewModel.openRegistration() },
            content = { Text(stringResource(R.string.sign_up)) })
    }
    Spacer(Modifier.size(16.dp * mod))

    if (viewModel.codeState.value.matched) {
        toastHost.sendToast(
            Icons.Rounded.DoneOutline,
            context.getString(R.string.welcome_user, viewModel.currentName)
        )
        viewModel.resetState()
    }
}