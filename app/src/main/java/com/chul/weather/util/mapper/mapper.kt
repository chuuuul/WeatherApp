package com.chul.weather.util.mapper

import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.data.model.WeatherResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun WeatherResponse.mapToWeatherInfo(): WeatherInfo {

    val localDate = LocalDate.parse(this.applicableDate, DateTimeFormatter.ISO_DATE)
    return WeatherInfo(localDate, this.minTemp, this.maxTemp, this.weatherStateAbbr)
}