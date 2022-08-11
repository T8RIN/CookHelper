package ru.tech.cookhelper.data.utils

import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class MoshiParser(private val moshi: Moshi) : JsonParser {

    override fun <T> toJson(obj: T, type: Type): String? {
        return moshi.adapter<T>(type).toJson(obj)
    }

    override fun <T> fromJson(json: String, type: Type): T? {
        return moshi.adapter<T>(type).fromJson(json)
    }

}