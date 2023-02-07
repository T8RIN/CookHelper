package ru.tech.cookhelper.presentation.ui.utils.android

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

object ContentUtils {

    fun <O> ManagedActivityResultLauncher<PickVisualMediaRequest, O>.pickImage() =
        launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

}