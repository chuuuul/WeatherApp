package com.chul.weather.data.source.local.room

import androidx.room.TypeConverter
import com.chul.weather.data.model.WeatherInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun weatherInfoList2json(weatherInfoList: List<WeatherInfo>): String {
        val gson = Gson()
        return gson.toJson(weatherInfoList)
    }

    @TypeConverter
    fun json2WeatherInfoList(json: String): List<WeatherInfo> {
        val listType = object : TypeToken<List<WeatherInfo>>() {}.type
        return Gson().fromJson(json, listType)
    }
}
