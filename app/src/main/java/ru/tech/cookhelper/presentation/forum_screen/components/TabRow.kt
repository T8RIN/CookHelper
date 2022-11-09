package ru.tech.cookhelper.presentation.forum_screen.components

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
import ru.tech.cookhelper.presentation.ui.utils.compose.customTabIndicatorOffset

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    selectedTabIndex: Int,
    indicatorType: IndicatorType = IndicatorType.FillText,
    tabs: List<Pair<String, ImageVector?>>,
    onTabClick: (index: Int) -> Unit,
    divider: @Composable () -> Unit = {}
) {
    val indicatorWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) { tabWidthStateList.add(0.dp) }
        tabWidthStateList
    }
    val density = LocalDensity.current
    Column {
        TabRow(
            modifier = modifier,
            containerColor = containerColor,
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                if (indicatorType is IndicatorType.FillText) {
                    TabRowDefaults.Indicator(
                        height = 3.dp,
                        modifier = Modifier
                            .customTabIndicatorOffset(
                                currentTabPosition = tabPositions[selectedTabIndex],
                                tabWidth = indicatorWidths[selectedTabIndex]
                            )
                            .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    )
                }
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
                                        indicatorWidths[tabIndex] =
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

@JvmName("TabRow1")
@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    selectedTabIndex: Int,
    indicatorType: IndicatorType = IndicatorType.FillText,
    tabs: List<String>,
    onTabClick: (index: Int) -> Unit,
    divider: @Composable () -> Unit = {}
) {
    TabRow(
        modifier = modifier,
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

sealed class IndicatorType {
    object Tonal : IndicatorType()
    object FillText : IndicatorType()
}