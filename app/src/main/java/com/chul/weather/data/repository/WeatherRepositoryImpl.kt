package com.chul.weather.data.repository

import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.data.source.remote.WeatherRemoteDataSource
import com.chul.weather.util.mapper.mapToWeatherInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate

class WeatherRepositoryImpl(private val weatherRemoteDataSource: WeatherRemoteDataSource) :
    WeatherRepository {

    override fun getWeather(locationCode: String, date: LocalDate?): Single<WeatherInfo> {
        val targetDate = date ?: LocalDate.now()
        return weatherRemoteDataSource.getWeather(locationCode, targetDate)
            .map { it[0].mapToWeatherInfo() }
            .subscribeOn(Schedulers.io())
    }

    override fun getWeather(
        locationCode: String,
        duration: Int,
        startDate: LocalDate?
    ): Observable<WeatherInfo> {

        val baseDate: LocalDate = startDate ?: LocalDate.now()
        val singleList = ArrayList<Single<WeatherInfo>>()

        var targetDate: LocalDate
        for (i in 0 until duration) {
            targetDate = baseDate.plusDays(i.toLong())
            val singleWeatherInfo = getWeather(locationCode, targetDate)
            singleList.add(singleWeatherInfo)
        }

        return Single.merge(singleList).toObservable().subscribeOn(Schedulers.io())
    }
}