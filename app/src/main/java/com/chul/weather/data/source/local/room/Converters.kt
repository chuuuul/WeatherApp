package com.chul.weather.data.source.local.room

import androidx.room.TypeConverter
import com.chul.weather.data.model.WeatherInfo
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import java.io.IOException
import java.time.LocalDate

class Converters {

    internal class LocalDateAdapter : TypeAdapter<LocalDate?>() {

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): LocalDate? {
            return if (jsonReader.peek() === JsonToken.NULL) {
                jsonReader.nextNull()
                null
            } else {
                LocalDate.parse(jsonReader.nextString())
            }
        }

        override fun write(jsonWriter: com.google.gson.stream.JsonWriter?, localDate: LocalDate?) {
            if (localDate == null) {
                jsonWriter?.nullValue()
            } else {
                jsonWriter?.value(localDate.toString())
            }
        }
    }

    private val gson =
        GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter().nullSafe())
            .create()

    @TypeConverter
    fun weatherInfoList2json(weatherInfoList: List<WeatherInfo>): String {
        return gson.toJson(weatherInfoList)
    }

    @TypeConverter
    fun json2WeatherInfoList(json: String): List<WeatherInfo> {
        val listType = object : TypeToken<List<WeatherInfo>>() {}.type
        return gson.fromJson(json, listType)
    }
}

