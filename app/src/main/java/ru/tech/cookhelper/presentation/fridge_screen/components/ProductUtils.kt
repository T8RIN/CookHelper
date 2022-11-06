package ru.tech.cookhelper.presentation.fridge_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.ui.graphics.vector.ImageVector
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.presentation.ui.theme.*

fun Product.getIcon(): ImageVector = when (this.category) {
    1 -> Icons.Filled.Steak
    2 -> Icons.Filled.Fish
    3 -> Icons.Filled.Milk
    4 -> Icons.Filled.Egg
    5 -> Icons.Filled.Carrot
    6 -> Icons.Filled.Apple
    7 -> Icons.Filled.Baguette
    8 -> Icons.Filled.Barley
    9 -> Icons.Filled.Shaker
    10 -> Icons.Filled.Candy
    11 -> Icons.Filled.Cup
    12 -> Icons.Filled.Bean
    13 -> Icons.Filled.Mushroom
    14 -> Icons.Filled.Jellyfish
    15 -> Icons.Filled.Flavour
    16 -> Icons.Filled.Peanut
    17 -> Icons.Filled.DriedGrape
    18 -> Icons.Filled.Cheese
    19 -> Icons.Filled.Cherry
    20 -> Icons.Filled.Oil
    else -> Icons.Filled.BorderRadius
}