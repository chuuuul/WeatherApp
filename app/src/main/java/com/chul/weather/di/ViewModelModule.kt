package com.chul.weather.di

import com.chul.weather.ui.weather.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
}