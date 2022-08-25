package ru.tech.cookhelper.presentation.profile.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@Composable
fun PostCreationBlock(onCreateRecipe: () -> Unit, onCreatePost: (imageUri: String) -> Unit) {
    val toastHost = LocalToastHost.current
    val imageNotPicked = stringResource(R.string.image_not_picked)

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri == null) {
                toastHost.sendToast(
                    icon = Icons.Outlined.BrokenImage,
                    message = imageNotPicked
                )
                return@rememberLauncherForActivityResult
            }
            onCreatePost(uri.toString())
        }
    )

    Column(
        Modifier.padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            stringResource(R.string.create).uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(Modifier.size(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                modifier = Modifier.weight(1f),
                onClick = onCreateRecipe
            ) {
                Text(stringResource(R.string.recipe))
            }
            Spacer(Modifier.size(5.dp))
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                modifier = Modifier.weight(1f),
                onClick = { onCreatePost("") }
            ) {
                Text(stringResource(R.string.post))
            }
            Spacer(Modifier.size(5.dp))
            FilledIconButton(
                modifier = Modifier.size(40.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                onClick = { resultLauncher.launch("image/*") }
            ) {
                Icon(Icons.Outlined.Image, null)
            }
        }
    }
}