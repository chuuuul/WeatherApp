package com.chul.weather.data.source.local

import com.chul.weather.data.model.WeatherEntity
import com.chul.weather.data.model.WeatherInfo
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherLocalDataSource {
    fun saveWeather(locationCode: String, weatherList: List<WeatherInfo>): Completable
    fun loadWeather(locationCode: String): Single<WeatherEntity>
    fun deleteWeather(locationCode: String): Completable
}