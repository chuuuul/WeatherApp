package com.chul.weather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherDB")
data class WeatherEntity(
    @PrimaryKey val locationCode: String,
    val weatherInfoList: List<WeatherInfo>
)