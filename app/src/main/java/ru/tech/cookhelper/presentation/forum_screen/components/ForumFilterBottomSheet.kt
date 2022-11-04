package ru.tech.cookhelper.presentation.forum_screen.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch
import ru.tech.cookhelper.R
import ru.tech.cookhelper.core.constants.Constants
import ru.tech.cookhelper.domain.model.ForumFilters
import ru.tech.cookhelper.presentation.app.components.CozyTextField
import ru.tech.cookhelper.presentation.app.components.TextFieldAppearance
import ru.tech.cookhelper.presentation.forum_discussion.components.TagItem
import ru.tech.cookhelper.presentation.recipe_post_creation.components.Separator
import ru.tech.cookhelper.presentation.settings.components.ToggleGroupButton
import ru.tech.cookhelper.presentation.ui.utils.compose.ColorUtils.harmonizeWithPrimary
import ru.tech.cookhelper.presentation.ui.utils.provider.LocalBottomSheetController
import ru.tech.cookhelper.presentation.ui.utils.provider.close

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ForumFilterBottomSheet(
    filters: ForumFilters,
    onFiltersChange: (ForumFilters) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val isAtTheTop by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }
    val bottomSheetController = LocalBottomSheetController.current
    var localFilters by rememberSaveable(saver = FilterSaver) { mutableStateOf(filters) }

    Column(
        Modifier
            .navigationBarsPadding()
            .imePadding()
    ) {
        AnimatedVisibility(visible = !isAtTheTop) {
            Separator()
        }
        LazyColumn(state = lazyListState) {
            item {
                Text(
                    text = stringResource(R.string.filter_by),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(Modifier.weight(1f)) {
                        FilterItem(
                            stringResource(R.string.no_answers),
                            checked = localFilters.noRepliesFilter,
                            onCheckedChange = {
                                localFilters = localFilters.copy(noRepliesFilter = it)
                            }
                        )
                        FilterItem(
                            stringResource(R.string.has_attachments),
                            checked = localFilters.imageFilter,
                            onCheckedChange = { localFilters = localFilters.copy(imageFilter = it) }
                        )
                        FilterItem(
                            stringResource(R.string.positive_rating),
                            checked = localFilters.ratingPositiveFilter,
                            onCheckedChange = {
                                localFilters = localFilters.copy(ratingPositiveFilter = it)
                            }
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        FilterItem(
                            stringResource(R.string.no_rating),
                            checked = localFilters.ratingNeutralFilter,
                            onCheckedChange = {
                                localFilters = localFilters.copy(ratingNeutralFilter = it)
                            }
                        )
                        FilterItem(
                            stringResource(R.string.negative_rating),
                            checked = localFilters.ratingNegativeFilter,
                            onCheckedChange = {
                                localFilters = localFilters.copy(ratingNegativeFilter = it)
                            }
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.sorted_by),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ToggleGroupButton(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        items = listOf(R.string.date, R.string.rating),
                        selectedIndex = when {
                            localFilters.recencySort -> 0
                            localFilters.ratingSort -> 1
                            else -> 0
                        },
                        indexChanged = {
                            when (it) {
                                0 -> localFilters =
                                    localFilters.copy(recencySort = true, ratingSort = false)
                                1 -> localFilters =
                                    localFilters.copy(ratingSort = true, recencySort = false)
                            }
                        }
                    )
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = localFilters.reverseSort,
                        onCheckedChange = {
                            localFilters =
                                localFilters.copy(reverseSort = !localFilters.reverseSort)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondaryContainer.harmonizeWithPrimary(),
                            checkmarkColor = MaterialTheme.colorScheme.onSecondaryContainer.harmonizeWithPrimary()
                        )
                    )
                    Text(
                        stringResource(R.string.reversed),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                var value by rememberSaveable { mutableStateOf("") }
                val updateTags = {
                    val tagFilters =
                        localFilters.tagFilter.split(Constants.DELIMITER)
                            .filter { s -> s.isNotEmpty() }.toMutableList()
                    if (!tagFilters.contains(value)) tagFilters.add(value)
                    localFilters =
                        localFilters.copy(tagFilter = tagFilters.joinToString(Constants.DELIMITER))
                    value = ""
                }
                val endIcon = @Composable {
                    IconButton(
                        onClick = { updateTags() }
                    ) {
                        Icon(Icons.Rounded.CheckCircleOutline, null)
                    }
                }
                Text(
                    text = stringResource(R.string.contains_tags),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(Modifier.padding(horizontal = 8.dp)) {
                    val tags =
                        remember(localFilters) {
                            localFilters.tagFilter.split(Constants.DELIMITER)
                                .filter { s -> s.isNotEmpty() }
                        }
                    AnimatedVisibility(
                        visible = tags.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        enter = fadeIn() + scaleIn(initialScale = 0.92f),
                        exit = fadeOut() + scaleOut(targetScale = 0.92f)
                    ) {
                        AnimatedContent(
                            targetState = tags,
                            transitionSpec = { fadeIn() with fadeOut() }
                        ) {
                            FlowRow(
                                modifier = Modifier.weight(1f),
                                mainAxisSpacing = 8.dp,
                                crossAxisSpacing = 8.dp,
                            ) {
                                it.forEach { text ->
                                    TagItem(
                                        text = text,
                                        onClick = {
                                            val tagFilters =
                                                localFilters.tagFilter.split(Constants.DELIMITER)
                                                    .toMutableList()
                                            tagFilters.remove(text)
                                            localFilters =
                                                localFilters.copy(
                                                    tagFilter = tagFilters.joinToString(
                                                        Constants.DELIMITER
                                                    )
                                                )
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.width(4.dp))
                    CozyTextField(
                        modifier = Modifier.weight(1f),
                        value = value,
                        appearance = TextFieldAppearance.Outlined,
                        onValueChange = {
                            value = it.trim()
                            if (it.contains("\n")) updateTags()
                        },
                        endIcon = if (value.isNotEmpty()) endIcon else null,
                        maxLines = 2,
                        label = stringResource(R.string.add_tag)
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            scope.launch { bottomSheetController.close() }
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {
                            onFiltersChange(localFilters)
                            scope.launch { bottomSheetController.close() }
                        }
                    ) {
                        Text(stringResource(R.string.apply))
                    }
                }
            }
        }
    }

    BackHandler {
        scope.launch { bottomSheetController.close() }
    }
}

@Composable
private fun FilterItem(
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(name)
    }
}


private val FilterSaver = listSaver<MutableState<ForumFilters>, String>(
    save = {
        it.value.run {
            listOf(
                queryString,
                noRepliesFilter.toString(),
                imageFilter.toString(),
                ratingNeutralFilter.toString(),
                ratingPositiveFilter.toString(),
                ratingNegativeFilter.toString(),
                tagFilter,
                ratingSort.toString(),
                recencySort.toString(),
                reverseSort.toString()
            )
        }
    },
    restore = {
        mutableStateOf(
            ForumFilters(
                it[0],
                it[1].toBoolean(),
                it[2].toBoolean(),
                it[3].toBoolean(),
                it[4].toBoolean(),
                it[5].toBoolean(),
                it[6],
                it[7].toBoolean(),
                it[8].toBoolean(),
                it[9].toBoolean(),
            )
        )
    }
)