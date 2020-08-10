package com.chul.weather.data.source.remote

import com.chul.weather.data.model.WeatherResponse
import com.chul.weather.util.retrofit.WeatherService
import io.reactivex.Single
import java.time.LocalDate

class WeatherRemoteDataSourceImpl(private val weatherService: WeatherService) :
    WeatherRemoteDataSource {
    override fun getWeather(locationCode: String, date: LocalDate): Single<List<WeatherResponse>> {
        val year = date.year
        val month = date.monthValue
        val day = date.dayOfMonth
        return weatherService.getWeather(locationCode, year, month, day)
    }

    override fun getWeather(
        locationCode: String,
        year: Int,
        month: Int,
        day: Int
    ): Single<List<WeatherResponse>> {
        return weatherService.getWeather(locationCode, year, month, day)
    }


}