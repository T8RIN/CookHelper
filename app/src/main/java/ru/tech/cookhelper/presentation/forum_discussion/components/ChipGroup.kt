package ru.tech.cookhelper.presentation.forum_discussion.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChipGroup(modifier: Modifier = Modifier, chips: List<String>, onChipClick: (String) -> Unit) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
        modifier = modifier
    ) {
        chips.forEach { chip ->
            TagItem(
                text = chip,
                onClick = { onChipClick(chip) }
            )
        }
    }
}