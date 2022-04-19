package ru.tech.prokitchen.data.remote.dto

import ru.tech.prokitchen.core.constants.Constants.DELIMETER
import ru.tech.prokitchen.core.constants.Constants.IMG_URL
import ru.tech.prokitchen.domain.model.Recipe

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
        products.removePrefix(DELIMETER).split(DELIMETER),
        itogProducts.split(DELIMETER).map { it.toInt() },
        calories,
        carboh,
        category,
        cookSteps.split("\\n"),
        cookTime,
        fats,
        proteins,
        source,
        title,
        IMG_URL.replace(DELIMETER, id.toString())
    )