package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class MatchedRecipe(
    val recipe: Recipe,
    val percentString: String
) : Domain