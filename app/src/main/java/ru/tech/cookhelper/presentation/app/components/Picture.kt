package ru.tech.cookhelper.presentation.app.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest

@Composable
fun Picture(
    modifier: Modifier = Modifier,
    model: Any? = null,
    manualImageRequest: ImageRequest? = null,
    contentDescription: String? = null,
    shape: CornerBasedShape = CircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    var shimmerVisible by rememberSaveable { mutableStateOf(true) }

    AsyncImage(
        model = manualImageRequest ?: ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape)
            .shimmer(shimmerVisible),
        contentScale = contentScale,
        onSuccess = { shimmerVisible = false; onSuccess?.invoke(it); onState?.invoke(it) },
        onLoading = { onLoading?.invoke(it); onState?.invoke(it) },
        onError = { onError?.invoke(it); onState?.invoke(it) },
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality
    )
}