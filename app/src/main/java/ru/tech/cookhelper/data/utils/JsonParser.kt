package ru.tech.cookhelper.data.utils

import java.lang.reflect.Type

interface JsonParser {

    /**
     * [type] is type of [obj]: [T], which is converted to json
     *
     * @return Json from given object
     */
    fun <T> toJson(obj: T, type: Type): String?

    /**
     * [type] is type of [T], which is will be parsed from json
     *
     * @return Object from given json
     */
    fun <T> fromJson(json: String, type: Type): T?

}