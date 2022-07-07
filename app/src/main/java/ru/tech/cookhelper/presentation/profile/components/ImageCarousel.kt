package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.constants.Constants.IMAGE_CAROUSEL_KEY
import ru.tech.cookhelper.domain.model.Image
import ru.tech.cookhelper.presentation.app.components.Picture
import ru.tech.cookhelper.presentation.ui.utils.rememberForeverLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCarousel(
    data: List<Image>,
    onImageClick: (id: Int) -> Unit,
    onAddImageClick: () -> Unit,
    onExpand: () -> Unit
) {
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
            Text(data.size.toString(), color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onExpand) {
                Icon(Icons.Rounded.KeyboardArrowRight, null)
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
            state = rememberForeverLazyListState(key = IMAGE_CAROUSEL_KEY)
        ) {
            itemsIndexed(data) { _, item ->
                OutlinedCard(
                    onClick = { onImageClick(item.id) },
                    modifier = Modifier.size(100.dp)
                ) {
                    Picture(
                        shape = RoundedCornerShape(0.dp),
                        model = item.link,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(Modifier.width(5.dp))
            }
            item {
                Card(onClick = onAddImageClick, modifier = Modifier.size(100.dp)) {
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