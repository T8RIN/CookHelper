package ru.tech.cookhelper.presentation.post_creation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.app.components.TopAppBar
import ru.tech.cookhelper.presentation.post_creation.viewModel.PostCreationViewModel
import ru.tech.cookhelper.presentation.ui.utils.scope.scopedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PostCreationScreen(
    viewModel: PostCreationViewModel = scopedViewModel(),
    initialImageUri: String = "",
    goBack: () -> Unit
) {
    val focus = LocalFocusManager.current
    var doneEnabled by rememberSaveable { mutableStateOf(false) }

    val user = viewModel.user.value

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focus.clearFocus() })
            }
    ) {
        TopAppBar(
            background = TopAppBarDefaults
                .smallTopAppBarColors()
                .containerColor(1f)
                .value,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Picture(model = user?.avatar, modifier = Modifier.size(40.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "${user?.name?.trim()} ${user?.surname?.trim()}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { goBack() }) {
                    Icon(Icons.Rounded.Close, null)
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        /*TODO: Done*/
                        goBack()
                    },
                    enabled = doneEnabled,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Rounded.Done, null)
                }
            }
        )

        val shape = RoundedCornerShape(4.dp)
        val paddingValues = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp)

        var postText by rememberSaveable { mutableStateOf("") }
        var postLabel by rememberSaveable { mutableStateOf("") }
        var imageUri by rememberSaveable { mutableStateOf(initialImageUri) }

        val resultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                it?.let { uri ->
                    imageUri = uri.toString()
                }
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = postLabel,
                onValueChange = {
                    postLabel = it
                },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                    .fillMaxWidth()
                    .animateContentSize(),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = shape,
                label = {
                    Text(stringResource(R.string.enter_headline))
                },
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                singleLine = true
            )

            TextField(
                value = postText,
                onValueChange = {
                    postText = it
                },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 32.dp)
                    .fillMaxWidth()
                    .animateContentSize(),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = shape,
                label = {
                    Text(
                        stringResource(R.string.whats_new),
                        modifier = Modifier.offset(y = 4.dp)
                    )
                },
                textStyle = TextStyle(fontSize = 20.sp)
            )

            AnimatedContent(targetState = imageUri) { uri ->
                if (uri.isEmpty()) {
                    OutlinedButton(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth(),
                        onClick = { resultLauncher.launch("image/*") }
                    ) {
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Outlined.Image, null)
                        Spacer(Modifier.width(16.dp))
                        Text(stringResource(R.string.add_image))
                        Spacer(Modifier.width(8.dp))
                    }
                } else {
                    Box {
                        Picture(
                            model = imageUri,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValues),
                            shape = RoundedCornerShape(24.dp),
                            contentScale = ContentScale.Fit
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 14.dp, top = 6.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiaryContainer)
                                .clickable(
                                    onClick = { imageUri = "" },
                                    role = Role.Button
                                )
                                .align(Alignment.TopEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Cancel,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(imageUri, postText) {
            doneEnabled = imageUri.isNotEmpty() || postText.isNotEmpty()
        }

    }

    BackHandler { goBack() }
}