package com.chul.weather.data.model

data class WeatherAdapterModel(
        val type: Int,
        val categoryInfo: CategoryInfo?,
        val weatherInfo: WeatherInfo?
) {
    companion object {
        const val category = 0
        const val weather = 1
    }
}

