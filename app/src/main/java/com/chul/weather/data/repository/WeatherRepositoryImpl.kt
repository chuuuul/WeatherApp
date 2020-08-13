package com.chul.weather.data.repository

import android.util.Log
import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.data.source.local.WeatherLocalDataSource
import com.chul.weather.data.source.remote.WeatherRemoteDataSource
import com.chul.weather.util.mapper.mapToWeatherInfo
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

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
    ): Flowable<List<WeatherInfo>> {

        val baseDate: LocalDate = startDate ?: LocalDate.now()
        var targetDate: LocalDate

        val local = weatherLocalDataSource.loadWeather(locationCode)
            .subscribeOn(Schedulers.io())
            .map {
                it.weatherInfoList
            }.onErrorReturn {
                listOf()
            }
            .doOnSuccess { list ->
                if (list.isNotEmpty()) {
                    Log.d("_chul", "캐시 데이터 불러오기 성공")
                } else {
                    Log.d("_chul", "캐시에 저장된 날씨가 없습니다.")
                }
            }

        val remote = Flowable.fromIterable(0..duration)
            .subscribeOn(Schedulers.newThread())
            .concatMapEager { afterDate ->
                targetDate = baseDate.plusDays(afterDate.toLong())
                getWeather(locationCode, targetDate).toFlowable()
            }.toList()
            .onErrorReturn {
                listOf()
            }
            .flatMap { weatherList ->
                weatherLocalDataSource.saveWeather(locationCode, weatherList)
                    .subscribeOn(Schedulers.io())
                    .andThen(Single.just(weatherList))
                    .doAfterSuccess { list ->
                        if (list.isNotEmpty()) {
                            Log.d("_chul", "캐시에 날씨 저장 : $weatherList")
                        }
                    }
                    .doOnSuccess { list ->
                        if (list.isEmpty()) {
                            Log.e("chul", "Remote Data 가져오기 실패")
                            return@doOnSuccess
                        } else {
                            Log.e("_chul", "Remote Data 가져오기 성공")
                        }
                    }
            }

        return local.concatWith(remote).subscribeOn(Schedulers.io())
    }
}