package com.chul.weather.data.source.local

import com.chul.weather.data.model.WeatherEntity
import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.data.source.local.room.WeatherDao
import io.reactivex.Completable
import io.reactivex.Single

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao) : WeatherLocalDataSource {

    override fun saveWeather(
        locationCode: String,
        weatherList: List<WeatherInfo>
    ) {
        val weatherEntity = WeatherEntity(locationCode, weatherList)
        weatherDao.insertWeather(weatherEntity)
    }

    override fun loadWeather(locationCode: String): Single<WeatherEntity> {
        return weatherDao.loadWeather(locationCode)
    }

    override fun deleteWeather(locationCode: String): Completable {
        return weatherDao.deleteWeather(WeatherEntity(locationCode, mutableListOf()))
    }
}