package com.chul.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chul.weather.R
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private val vm: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        vm.getWeather()
    }
}