package com.chul.weather.data.repository

import com.chul.weather.data.model.WeatherInfo
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.LocalDate

interface WeatherRepository {
    fun getWeather(locationCode: String, date: LocalDate? = null): Single<WeatherInfo>

    fun getWeather(locationCode: String, duration: Int, startDate: LocalDate? = null)
            : Flowable<List<WeatherInfo>>

}