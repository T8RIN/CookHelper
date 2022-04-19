package ru.tech.prokitchen.domain.model

import ru.tech.prokitchen.core.constants.Constants.BASE_URL

data class Recipe(
    val id: Int,
    val products: List<String>,
    val productIds: List<Int>,
    val calories: Double,
    val carboh: Double,
    val category: String,
    val cookSteps: List<String>,
    val cookTime: Int,
    val fats: Double,
    val proteins: Double,
    val source: String,
    val title: String,
    val iconUrl: String
) {
    fun toShareValue(): String {
        val n = "\n\n"
        return "$title${n}Категория - $category${n}Время приготовления - $cookTime мин${n}Б/Ж/У - $proteins/$fats/${carboh}${n}Калории - ${calories}$n${products.joinToString()}${n}${
            cookSteps.joinToString(n)
        }${BASE_URL}recipe/$id"
    }
}