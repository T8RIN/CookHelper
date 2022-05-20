package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.core.constants.Constants.DELIMITER
import ru.tech.cookhelper.core.constants.Constants.recipeImageFor
import ru.tech.cookhelper.domain.model.Recipe

data class RecipeDto(
    val calories: Double,
    val carboh: Double,
    val category: String,
    val cookSteps: String,
    val itogProducts: String,
    val cookTime: Int,
    val fats: Double,
    val id: Int,
    val products: String,
    val proteins: Double,
    val source: String,
    val title: String
)

fun RecipeDto.toRecipe(): Recipe =
    Recipe(
        id,
        products.removePrefix(DELIMITER).split(DELIMITER),
        itogProducts.split(DELIMITER).map { it.toInt() },
        calories,
        carboh,
        category,
        cookSteps.split("\\n"),
        cookTime,
        fats,
        proteins,
        source,
        title,
        recipeImageFor(id)
    )