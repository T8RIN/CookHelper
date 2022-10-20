package ru.tech.cookhelper.data.local.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Types
import ru.tech.cookhelper.data.utils.JsonParser
import javax.inject.Inject

@ProvidedTypeConverter
class TypeConverters @Inject constructor(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromStringList(data: List<String>): String {
        return jsonParser.toJson(
            data, Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        ) ?: ""
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return jsonParser.fromJson<List<String>>(
            data, Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        ) ?: emptyList()
    }
}
