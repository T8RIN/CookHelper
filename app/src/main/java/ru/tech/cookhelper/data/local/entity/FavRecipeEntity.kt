package ru.tech.cookhelper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavRecipeEntity(
    @PrimaryKey val id: Int,
//    val products: List<String>,
//    val productIds: List<Int>,
//    val calories: Double,
//    val carboh: Double,
//    val category: String,
//    val cookSteps: List<String>,
//    val cookTime: Int,
//    val fats: Double,
//    val proteins: Double,
//    val source: String,
//    val title: String,
//    val iconUrl: String
)
