package com.chul.weather.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chul.weather.data.model.WeatherEntity

@Database(entities = [WeatherEntity::class], exportSchema = false, version = 1)
@TypeConverters(value = [Converters::class])
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao

}
