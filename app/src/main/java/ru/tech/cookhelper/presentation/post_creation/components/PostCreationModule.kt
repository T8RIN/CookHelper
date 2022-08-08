package ru.tech.cookhelper.presentation.post_creation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import ru.tech.cookhelper.R
import ru.tech.cookhelper.presentation.app.components.Picture

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PostCreationModule(
    onChange: (text: String, label: String, uri: Uri) -> Unit
) {
    val shape = RoundedCornerShape(4.dp)
    val paddingValues = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp)

    var postText by rememberSaveable { mutableStateOf("") }
    var postLabel by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf("") }

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                imageUri = uri.toString()
                onChange(postText, postLabel, uri)
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
                onChange(postText, postLabel, imageUri.toUri())
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
                onChange(postText, postLabel, imageUri.toUri())
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
                OutlinedCard(
                    onClick = { resultLauncher.launch("image/*") },
                    shape = shape,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) {
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Rounded.AddAPhoto, null)
                        Spacer(Modifier.width(16.dp))
                        Text(stringResource(R.string.add_image))
                        Spacer(Modifier.width(8.dp))
                    }
                    Spacer(Modifier.height(8.dp))
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
}