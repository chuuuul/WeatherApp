package com.chul.weather.util.retrofit

import com.chul.weather.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("/api/location/{loc_code}/{year}/{month}/{day}")
    fun getWeather(
        @Path("loc_code") locationCode: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Single<List<WeatherResponse>>
}