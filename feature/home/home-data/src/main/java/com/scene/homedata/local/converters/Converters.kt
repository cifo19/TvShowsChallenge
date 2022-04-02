package com.scene.homedata.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scene.homedata.local.entity.TvShowData
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromStringListConverter(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromIntListConverter(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromInt(value: String): List<Int> {
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringToTvShowData(value: String): List<TvShowData> {
        val tvShowDataListType: Type = object : TypeToken<List<TvShowData>>() {}.type
        return Gson().fromJson(value, tvShowDataListType)
    }

    @TypeConverter
    fun fromTvShowDataListToString(tvShowData: List<TvShowData>): String {
        return Gson().toJson(tvShowData)
    }
}
