package ru.tech.cookhelper.core.constants

object Constants {

    val DOTS = "." * 100

    const val DELIMITER = "*"

    const val HOST_URL = "192.168.43.51:8080"

    const val BASE_URL = "http://$HOST_URL/"

    const val WS_BASE_URL = "ws://$HOST_URL/"

    const val LOREM_IPSUM =
        "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

}

private operator fun String.times(count: Int): String {
    var s = this
    repeat(count) { s += this }
    return s
}
