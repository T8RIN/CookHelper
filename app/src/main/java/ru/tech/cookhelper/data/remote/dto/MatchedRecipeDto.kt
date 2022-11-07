package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.MatchedRecipe
import ru.tech.cookhelper.domain.model.Recipe

data class MatchedRecipeDto(
    val recipe: Recipe,
    val percentString: String
) : Dto {
    override fun asDomain(): MatchedRecipe = MatchedRecipe(
        recipe = recipe,
        percentString = percentString
    )
}