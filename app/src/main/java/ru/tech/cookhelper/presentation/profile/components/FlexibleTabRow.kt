package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.forum_screen.components.IndicatorType
import ru.tech.cookhelper.presentation.ui.utils.compose.customTabIndicatorOffset

@Composable
fun FlexibleTabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    selectedTabIndex: Int,
    tabs: List<Pair<String, ImageVector?>>,
    indicatorType: IndicatorType = IndicatorType.FillText,
    edgePadding: Dp = 0.dp,
    onTabClick: (index: Int) -> Unit,
    divider: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) { tabWidthStateList.add(0.dp) }
        tabWidthStateList
    }
    Column {
        ScrollableTabRow(
            containerColor = containerColor,
            modifier = modifier,
            selectedTabIndex = selectedTabIndex,
            edgePadding = edgePadding,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    height = 3.dp,
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedTabIndex],
                            tabWidth = tabWidths[selectedTabIndex]
                        )
                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                )
            },
            divider = { }
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                val icon = @Composable { Icon(tab.second!!, null) }
                val factor = animateFloatAsState(
                    targetValue = if (tabIndex == selectedTabIndex) 1f else 0f
                ).value

                Box {
                    if (indicatorType is IndicatorType.Tonal) {
                        Box(
                            Modifier
                                .padding(8.dp)
                                .height(if (tab.second == null) 48.dp else 72.dp)
                                .fillMaxWidth()
                                .scale(factor)
                                .clip(CircleShape)
                                .background(
                                    color = MaterialTheme.colorScheme
                                        .surfaceColorAtElevation(
                                            elevation = 6.dp
                                        )
                                        .copy(alpha = factor)
                                )
                        )
                    }
                    CompositionLocalProvider(
                        LocalRippleTheme provides if (indicatorType is IndicatorType.Tonal) Ripple else LocalRippleTheme.current
                    ) {
                        Tab(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape),
                            selected = selectedTabIndex == tabIndex,
                            onClick = {
                                onTabClick(tabIndex)
                            },
                            text = {
                                Text(
                                    text = tab.first,
                                    onTextLayout = { textLayoutResult ->
                                        tabWidths[tabIndex] =
                                            with(density) { textLayoutResult.size.width.toDp() }
                                    }
                                )
                            },
                            icon = if (tab.second != null) icon else null
                        )
                    }
                }
            }
        }
        divider()
    }
}

@JvmName("FlexibleTabRow1")
@Composable
fun FlexibleTabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    selectedTabIndex: Int,
    tabs: List<String>,
    indicatorType: IndicatorType = IndicatorType.FillText,
    edgePadding: Dp = 0.dp,
    onTabClick: (index: Int) -> Unit,
    divider: @Composable () -> Unit
) {
    FlexibleTabRow(
        modifier = modifier,
        edgePadding = edgePadding,
        containerColor = containerColor,
        indicatorType = indicatorType,
        selectedTabIndex = selectedTabIndex,
        tabs = tabs.map { it to null },
        onTabClick = onTabClick,
        divider = divider
    )
}

private object Ripple : RippleTheme {
    @Composable
    override fun defaultColor() = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.025f, 0.025f, 0.025f, 0.025f)
}