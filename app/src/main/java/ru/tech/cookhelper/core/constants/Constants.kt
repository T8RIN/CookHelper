package ru.tech.cookhelper.core.constants

object Constants {

    const val DELIMITER = "*"

    const val BASE_URL = "https://cook-helper-itl.herokuapp.com/"

    private const val IMG_URL = "https://kastybiy.herokuapp.com/static/img/recipe_${DELIMITER}.jpg"

    fun recipeImageFor(id: Int): String {
        return IMG_URL.replace(DELIMITER, id.toString())
    }

}