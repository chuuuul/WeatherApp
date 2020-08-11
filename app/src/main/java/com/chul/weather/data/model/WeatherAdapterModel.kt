package com.chul.weather.data.model

class WeatherAdapterModel private constructor(
        val type: Int,
        val categoryInfo: CategoryInfo?,
        val weatherInfo: WeatherInfo?
) {
    companion object {
        const val category = 0
        const val weather = 1


        operator fun invoke(categoryInfo: CategoryInfo): WeatherAdapterModel {
            return WeatherAdapterModel(category, categoryInfo, null)
        }

        operator fun invoke(weatherInfo: WeatherInfo): WeatherAdapterModel {
            return WeatherAdapterModel(weather, null, weatherInfo)
        }
    }
}