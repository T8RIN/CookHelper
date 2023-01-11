package ru.tech.cookhelper.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.app.components.NavigationBarsSpacer
import ru.tech.cookhelper.presentation.settings.components.*
import ru.tech.cookhelper.presentation.settings.viewModel.SettingsViewModel
import ru.tech.cookhelper.presentation.ui.utils.navigation.Dialog
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalDialogController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalTopAppBarActions
import ru.tech.cookhelper.presentation.ui.utils.provider.setActions
import ru.tech.cookhelper.presentation.ui.utils.provider.show

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    settingsState: SettingsState
) {
    val dialogController = LocalDialogController.current

    LocalTopAppBarActions.current.setActions {
        IconButton(
            onClick = { dialogController.show(Dialog.AboutApp) },
            content = { Icon(Icons.Outlined.HelpOutline, null) }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        with(settingsState) {
            ChangeLanguageOption(viewModel::insertSetting)
            ThemeOption(viewModel::insertSetting)
            ColorSchemeOption(viewModel::insertSetting)
            ThemePreviewOption()
            DynamicColorsOption(viewModel::insertSetting)
            FontSizeOption(viewModel::insertSetting)
            CartConnectionOption(viewModel::insertSetting)
            AppInfoVersionOption()

            NavigationBarsSpacer()
        }
    }

}