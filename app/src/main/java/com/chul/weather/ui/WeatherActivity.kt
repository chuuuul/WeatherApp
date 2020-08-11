package com.chul.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chul.weather.R
import com.chul.weather.databinding.ActivityWeatherBinding
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity() {

    private val vm: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)

        binding.lifecycleOwner = this
        binding.vm = vm

        settingInit()
    }

    private fun settingInit() {
        settingAdapter()
    }

    private fun settingAdapter() {
        rv_weather_list.adapter = WeatherAdapter()
    }
}