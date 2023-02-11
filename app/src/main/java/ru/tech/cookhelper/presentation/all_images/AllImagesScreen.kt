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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.presentation.all_images.components.AdaptiveVerticalGrid
import ru.tech.cookhelper.presentation.fullscreen_image_pager.FileDataSaver
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.utils.android.ContentUtils.pickImage
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.compose.TopAppBarUtils.topAppBarScrollBehavior
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.navigation.Screen
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState
import ru.tech.cookhelper.presentation.ui.utils.provider.goBack
import ru.tech.cookhelper.presentation.ui.utils.provider.navigate
import ru.tech.cookhelper.presentation.ui.widgets.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllImagesScreen(
    initial: List<FileData>,
    canAddImages: Boolean,
    onAddImage: MutableState<String?>
) {
    val images by rememberSaveable(initial, saver = FileDataSaver) { mutableStateOf(initial) }
    val screenController = LocalScreenController.current
    val onBack: () -> Unit = { screenController.goBack() }

    val scrollBehavior = topAppBarScrollBehavior()
    val context = LocalContext.current
    val toastHost = LocalToastHostState.current

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) {
                toastHost.show(
                    icon = Icons.Outlined.BrokenImage,
                    message = (R.string.image_not_picked).asString(context)
                )
                return@rememberLauncherForActivityResult
            }
            onAddImage.value = uri.toString()
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
                    Text(images.size.toString(), color = Gray)
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
                        resultLauncher.pickImage()
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
