package ru.tech.cookhelper.presentation.confirm_email

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.authentication.components.OTPField
import ru.tech.cookhelper.presentation.confirm_email.viewModel.ConfirmEmailViewModel
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.utils.compose.UIText
import ru.tech.cookhelper.presentation.ui.utils.event.Event
import ru.tech.cookhelper.presentation.ui.utils.event.collectWithLifecycle
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.*
import java.util.*

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ConfirmEmailField(
    authController: NavController<Screen>,
    scaleModifier: Float,
    name: String, email: String, token: String,
    onTitleChange: (UIText) -> Unit,
    viewModel: ConfirmEmailViewModel = hiltViewModel(
        defaultArguments = bundleOf("name" to name, "email" to email, "token" to token)
    )
) {
    val screenController = LocalScreenController.current
    val toastHost = LocalToastHost.current
    val width = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current

    val size = when {
        width >= 16 * 5 + 50 * 6 -> 50
        else -> ((width - 16 * 5) / 6)
    }

    BackHandler { authController.goBack() }

    Text(
        stringResource(R.string.last_step,
            viewModel.name.replaceFirstChar { it.titlecase(Locale.getDefault()) }),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.size(8.dp * scaleModifier))
    Text(
        stringResource(R.string.code_sent_to_your_email, viewModel.email),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.size(64.dp * scaleModifier))

    OTPField(
        length = 6,
        codeState = viewModel.codeState,
        onFilled = {
            viewModel.checkVerificationCode(it)
        }
    )

    Spacer(Modifier.size(16.dp * scaleModifier))
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
    Spacer(Modifier.size(48.dp * scaleModifier))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.mistaken),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Spacer(Modifier.size(12.dp))
        TextButton(
            onClick = { authController.navigate(Screen.Authentication.Register) },
            content = { Text(stringResource(R.string.sign_up)) })
    }
    Spacer(Modifier.size(16.dp * scaleModifier))

    viewModel.eventFlow.collectWithLifecycle {
        when (it) {
            is Event.ShowToast -> toastHost.sendToast(
                it.icon,
                it.text.asString(context)
            )
            is Event.NavigateIf -> {
                if (it.predicate(screenController.currentDestination)) {
                    screenController.navigate(it.screen)
                    onTitleChange(Screen.Home.Feed.title)
                }
            }
            else -> {}
        }
    }
}