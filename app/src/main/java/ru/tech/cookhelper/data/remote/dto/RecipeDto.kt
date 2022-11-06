package ru.tech.cookhelper.data.remote.dto

import ru.tech.cookhelper.data.remote.utils.Dto
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.model.User

data class RecipeDto(
    val id: Long = 0,
    val author: User,
    val title: String,
    val cookSteps: List<String>,
    val time: Long,
    val category: String,
    val ingredients: List<Product>,
    val values: List<Double>,
    val proteins: Double,
    val carbohydrates: Double,
    val fats: Double,
    val calories: Double,
    val image: FileData,
    val comments: List<Long> = listOf(),
    val reposts: List<Long> = listOf(),
    val likes: List<Long> = listOf(),
    val timestamp: Long
) : Dto {
    override fun asDomain(): Recipe = Recipe(
        id = id,
        author = author,
        title = title,
        cookSteps = cookSteps,
        time = time,
        category = category,
        ingredients = ingredients,
        values = values,
        proteins = proteins,
        carbohydrates = carbohydrates,
        fats = fats,
        calories = calories,
        image = image,
        comments = comments,
        reposts = reposts,
        likes = likes,
        timestamp = timestamp
    )
}