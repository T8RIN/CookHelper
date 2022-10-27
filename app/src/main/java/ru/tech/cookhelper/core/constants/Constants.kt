package ru.tech.cookhelper.core.constants

object Constants {

    const val DELIMITER = "*"

    const val BASE_URL = "https://cookhelper-inc.herokuapp.com/"

    const val WS_BASE_URL = "ws://cookhelper-inc.herokuapp.com/"

    const val LOREM_IPSUM =
        "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val IMG_URL = "https://kastybiy.herokuapp.com/static/img/recipe_${DELIMITER}.jpg"

    fun recipeImageFor(id: Int): String {
        return IMG_URL.replace(DELIMITER, id.toString())
    }

}