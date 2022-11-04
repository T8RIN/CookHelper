package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class ForumFilters(
    val queryString: String,
    val noRepliesFilter: Boolean,
    val imageFilter: Boolean,
    val ratingNeutralFilter: Boolean,
    val ratingPositiveFilter: Boolean,
    val ratingNegativeFilter: Boolean,
    val tagFilter: String,
    val ratingSort: Boolean,
    val recencySort: Boolean,
    val reverseSort: Boolean,
) : Domain {
    companion object {
        fun empty() = ForumFilters(
            queryString = "",
            noRepliesFilter = false,
            imageFilter = false,
            ratingNeutralFilter = false,
            ratingPositiveFilter = false,
            ratingNegativeFilter = false,
            tagFilter = "",
            ratingSort = false,
            recencySort = true,
            reverseSort = false
        )
    }
}
