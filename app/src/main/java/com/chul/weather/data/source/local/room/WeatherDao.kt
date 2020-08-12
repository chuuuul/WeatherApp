package com.chul.weather.data.source.local.room

import androidx.room.*
import com.chul.weather.data.model.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity): Completable

    @Query("SELECT * FROM weatherDB WHERE `locationCode` = :locationCode")
    fun loadWeather(locationCode: String): Single<WeatherEntity>

    @Delete
    fun deleteWeather(weatherEntity: WeatherEntity): Completable
}