package ru.tech.cookhelper.presentation.all_images

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.navigate
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.all_images.components.AdaptiveVerticalGrid
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.app.components.sendToast
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarStateUtils.rememberTopAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllImagesScreen(
    images: List<Image>,
    canAddImages: Boolean,
    onBack: () -> Unit,
    onAddImage: (uri: String) -> Unit
) {
    val screenController = LocalScreenController.current
    val scrollBehavior = rememberTopAppBarScrollBehavior()
    val context = LocalContext.current
    val toastHost = LocalToastHost.current

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri == null) {
                toastHost.sendToast(
                    icon = Icons.Outlined.BrokenImage,
                    message = (R.string.image_not_picked).asString(context)
                )
                return@rememberLauncherForActivityResult
            }
            onAddImage(uri.toString())
        }
    )

    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        TopAppBar(
            title = {
                Row {
                    Text(
                        stringResource(R.string.photos),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.size(5.dp))
                    Text(images.size.toString(), color = Color.Gray)
                }
            },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Rounded.ArrowBack, null)
                }
            },
            actions = {
                if (canAddImages) {
                    IconButton(onClick = {
                        resultLauncher.launch("image/*")
                    }) {
                        Icon(Icons.Rounded.Add, null)
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
        AdaptiveVerticalGrid(
            images,
            onImageClick = {
                screenController.navigate(
                    Screen.FullscreenImagePager(
                        id = it,
                        images = images,
                    )
                )
            }
        )
    }

    BackHandler { onBack() }

}
