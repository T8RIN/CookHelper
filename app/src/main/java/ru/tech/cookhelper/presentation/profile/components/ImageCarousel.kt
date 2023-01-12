package ru.tech.cookhelper.presentation.profile.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.theme.Gray
import ru.tech.cookhelper.presentation.ui.theme.SquircleShape
import ru.tech.cookhelper.presentation.ui.utils.compose.ResUtils.asString
import ru.tech.cookhelper.presentation.ui.utils.compose.show
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalToastHostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCarousel(
    imageSize: Dp = 100.dp,
    data: List<FileData>,
    onImageClick: (id: String) -> Unit,
    onAddImage: (imageUri: String) -> Unit,
    onExpand: () -> Unit
) {
    val toastHost = LocalToastHostState.current
    val context = LocalContext.current
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri == null) {
                toastHost.show(
                    icon = Icons.Outlined.BrokenImage,
                    message = (R.string.image_not_picked).asString(context)
                )
                return@rememberLauncherForActivityResult
            }
            onAddImage(uri.toString())
        }
    )

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.photos).uppercase(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(Modifier.size(5.dp))
            Text(data.size.toString(), color = Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onExpand) {
                Icon(Icons.Rounded.KeyboardArrowRight, null)
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
        ) {
            items(data, key = { it.id }) { item ->
                OutlinedCard(
                    onClick = { onImageClick(item.id) },
                    modifier = Modifier.size(imageSize),
                    shape = SquircleShape(12.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Picture(
                        shape = RectangleShape,
                        model = item.link,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.width(5.dp))
            }
            item {
                Card(
                    onClick = { resultLauncher.launch("image/*") },
                    modifier = Modifier.size(imageSize),
                    shape = SquircleShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.AddCircle, null)
                        Spacer(Modifier.size(5.dp))
                        Text(
                            stringResource(R.string.add),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}