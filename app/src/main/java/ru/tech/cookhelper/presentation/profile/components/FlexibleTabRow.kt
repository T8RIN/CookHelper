package ru.tech.cookhelper.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.tech.cookhelper.presentation.ui.utils.compose.customTabIndicatorOffset

@Composable
fun FlexibleTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
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
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    height = 4.dp,
                    modifier = Modifier
                        .customTabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedTabIndex],
                            tabWidth = tabWidths[selectedTabIndex]
                        )
                        .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
                )
            },
            divider = { }
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
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
                            text = tab,
                            onTextLayout = { textLayoutResult ->
                                tabWidths[tabIndex] =
                                    with(density) { textLayoutResult.size.width.toDp() }
                            }
                        )
                    }
                )
            }
        }
        divider()
    }
}