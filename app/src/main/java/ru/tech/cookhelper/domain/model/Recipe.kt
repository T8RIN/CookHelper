package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.core.constants.Constants.BASE_URL
import ru.tech.cookhelper.domain.utils.Domain

data class Recipe(
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
) : Domain {
    fun toShareValue(): String {
        val n = "\n\n"
        return "$title${n}Категория - $category${n}Время приготовления - $time мин${n}Б/Ж/У - $proteins/$fats/${carbohydrates}${n}Калории - ${calories}$n${
            ingredients.joinToString(
                ", "
            ) { it.title }
        }${n}${
            cookSteps.joinToString(n)
        }${BASE_URL}recipe/$id"
    }
}