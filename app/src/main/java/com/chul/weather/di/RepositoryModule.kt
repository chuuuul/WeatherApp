package com.chul.weather.di

import com.chul.weather.data.repository.WeatherRepositoryImpl
import com.chul.weather.data.source.remote.WeatherRemoteDataSource
import com.chul.weather.data.source.remote.WeatherRemoteDataSourceImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }
    single<WeatherRepositoryImpl> { WeatherRepositoryImpl(get()) }

}