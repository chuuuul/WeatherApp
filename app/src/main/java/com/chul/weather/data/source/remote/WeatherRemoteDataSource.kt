package com.chul.weather.data.source.remote

import com.chul.weather.data.model.WeatherResponse
import io.reactivex.Single
import java.time.LocalDate

interface WeatherRemoteDataSource {
    fun getWeather(locationCode: String, date: LocalDate): Single<List<WeatherResponse>>
    fun getWeather(
        locationCode: String,
        year: Int,
        month: Int,
        day: Int
    ): Single<List<WeatherResponse>>
}