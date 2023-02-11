package ru.tech.cookhelper.presentation.fullscreen_image_pager

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.twotone.SignalWifiConnectedNoInternet4
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import ru.tech.cookhelper.R
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.presentation.ui.utils.android.ContextUtils.findActivity
import ru.tech.cookhelper.presentation.ui.utils.android.SystemBarUtils.showSystemBars
import ru.tech.cookhelper.presentation.ui.utils.compose.widgets.Picture
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalScreenController
import ru.tech.cookhelper.presentation.ui.utils.provider.goBack
import ru.tech.cookhelper.presentation.ui.widgets.AnimatedTopAppBar
import ru.tech.cookhelper.presentation.ui.widgets.Loading
import ru.tech.cookhelper.presentation.ui.widgets.Placeholder
import ru.tech.cookhelper.presentation.ui.widgets.zooomable.ZoomParams

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullScreenPagerScreen(
    imageList: List<FileData>,
    initialId: String
) {
    val controller = LocalScreenController.current
    val activity = LocalContext.current.findActivity()
    val onBack: () -> Unit = {
        controller.goBack()
        activity?.showSystemBars()
    }

    val images by rememberSaveable(imageList, saver = FileDataSaver) { mutableStateOf(imageList) }

    val pagerState = rememberPagerState(images.indexOfFirst { it.id == initialId })

    var isTopBarHidden by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = images.size,
            state = pagerState
        ) { page ->
            Picture(
                zoomParams = ZoomParams(
                    zoomEnabled = true,
                    hideBarsOnTap = true,
                    onTap = { isTopBarHidden = !isTopBarHidden }
                ),
                shimmerEnabled = false,
                model = images[page].link,
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                contentScale = ContentScale.Fit,
                loading = { Loading() },
                error = {
                    Placeholder(
                        icon = Icons.TwoTone.SignalWifiConnectedNoInternet4,
                        text = stringResource(R.string.no_connection)
                    )
                }
            )
        }
        AnimatedTopAppBar(
            background = Color.Black.copy(alpha = 0.5f),
            title = {
                Text(
                    stringResource(
                        R.string.count_of_all,
                        pagerState.currentPage + 1,
                        pagerState.pageCount
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        null,
                        tint = Color.White
                    )
                }
            },
            visible = !isTopBarHidden,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        )
        AnimatedVisibility(
            visible = !isTopBarHidden,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                    activeColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    inactiveColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
    BackHandler { onBack() }
}
