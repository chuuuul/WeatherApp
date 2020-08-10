package com.chul.weather.data.model

import java.util.*

// 최저온도, 최대온도, 상태, 날짜,
data class RecentWeather(
    val weathers: ArrayList<WeatherInfo>
) {
    data class WeatherInfo(
        val date: Date,
        val minTemp: Float,
        val maxTemp: Float,
        val weatherStateAbbr: String
    )
}