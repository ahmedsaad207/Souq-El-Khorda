package com.delighted2wins.souqelkhorda.features.sell.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromList(images: List<String>): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toList(imagesString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(imagesString, type) ?: emptyList()
    }
}