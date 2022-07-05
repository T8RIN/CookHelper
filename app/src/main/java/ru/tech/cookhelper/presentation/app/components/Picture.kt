package ru.tech.cookhelper.presentation.app.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import ru.tech.cookhelper.presentation.ui.utils.zooomable.Zoomable
import ru.tech.cookhelper.presentation.ui.utils.zooomable.rememberZoomableState

@Composable
fun Picture(
    modifier: Modifier = Modifier,
    model: Any?,
    manualImageRequest: ImageRequest? = null,
    contentDescription: String? = null,
    shape: Shape = CircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    loading: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit)? = null,
    success: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Success) -> Unit)? = null,
    error: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = null,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    zoomEnabled: Boolean = false,
    minZoomScale: Float = 1f,
    maxZoomScale: Float = 4f,
    shimmerEnabled: Boolean = true
) {
    var errorOccurred by rememberSaveable { mutableStateOf(false) }

    var shimmerVisible by rememberSaveable { mutableStateOf(true) }

    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (SDK_INT >= 28) add(ImageDecoderDecoder.Factory())
        else add(GifDecoder.Factory())
    }.build()

    val request = manualImageRequest ?: ImageRequest.Builder(LocalContext.current)
        .data(model)
        .crossfade(true)
        .build()

    val image: @Composable () -> Unit = {
        SubcomposeAsyncImage(
            model = request,
            imageLoader = imageLoader,
            contentDescription = contentDescription,
            modifier = modifier
                .clip(shape)
                .then(if (shimmerEnabled) Modifier.shimmer(shimmerVisible) else Modifier),
            contentScale = contentScale,
            loading = loading,
            success = success,
            error = error,
            onSuccess = {
                shimmerVisible = false
                onSuccess?.invoke(it)
                onState?.invoke(it)
            },
            onLoading = {
                onLoading?.invoke(it)
                onState?.invoke(it)
            },
            onError = {
                if (error != null) shimmerVisible = false
                onError?.invoke(it)
                onState?.invoke(it)
                errorOccurred = true
            },
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality
        )
    }

    if (zoomEnabled) {
        Zoomable(
            state = rememberZoomableState(
                minScale = minZoomScale,
                maxScale = maxZoomScale
            ),
            content = { image() }
        )
    } else image()

    LaunchedEffect(errorOccurred) {
        if (errorOccurred && error == null) {
            shimmerVisible = false
            shimmerVisible = true
            errorOccurred = false
        }
    }

}