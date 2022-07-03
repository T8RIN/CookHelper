package ru.tech.cookhelper.presentation.profile

import androidx.compose.runtime.Composable
import ru.tech.cookhelper.presentation.app.components.MainModalDrawerHeader
import ru.tech.cookhelper.presentation.profile.viewModel.ProfileViewModel
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = scopedViewModel()) {
    MainModalDrawerHeader(userState = viewModel.userState.value, onClick = {})
}