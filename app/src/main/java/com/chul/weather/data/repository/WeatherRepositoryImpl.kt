package com.chul.weather.data.repository

import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.data.source.remote.WeatherRemoteDataSource
import com.chul.weather.util.mapper.mapToWeatherInfo
import io.reactivex.Flowable
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
    ): Single<List<WeatherInfo>> {
        val baseDate: LocalDate = startDate ?: LocalDate.now()
        var targetDate: LocalDate

        return Flowable.fromIterable(0..duration)
                .concatMapEager { afterDate ->
                    targetDate = baseDate.plusDays(afterDate.toLong())
                    getWeather(locationCode, targetDate).toFlowable()
                }.toList()
    }
}