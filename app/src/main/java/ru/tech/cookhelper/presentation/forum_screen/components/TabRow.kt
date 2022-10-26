package ru.tech.cookhelper.presentation.forum_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                TabRowDefaults.Indicator(
                    height = 3.dp,
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedTabIndex],
                            tabWidth = indicatorWidths[selectedTabIndex]
                        )
                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                )
            },
            divider = { }
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                val icon = @Composable { Icon(tab.second!!, null) }
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
        divider()
    }
}

@JvmName("TabRow1")
@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabClick: (index: Int) -> Unit,
    divider: @Composable () -> Unit = {}
) {
    TabRow(
        modifier = modifier,
        containerColor = containerColor,
        selectedTabIndex = selectedTabIndex,
        tabs = tabs.map { it to null },
        onTabClick = onTabClick,
        divider = divider
    )
}