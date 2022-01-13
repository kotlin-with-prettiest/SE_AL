package com.example.se_al.data

import androidx.room.TypeConverter
import com.google.gson.Gson

// list type을 쓰기위한 converter

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}