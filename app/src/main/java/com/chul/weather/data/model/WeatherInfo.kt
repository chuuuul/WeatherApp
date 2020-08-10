package com.chul.weather.data.model

import java.time.LocalDate

data class WeatherInfo(
    val date: LocalDate,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherStateAbbr: String
)