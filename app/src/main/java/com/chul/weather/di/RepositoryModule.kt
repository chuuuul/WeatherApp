package com.chul.weather.di

import androidx.room.Room
import com.chul.weather.data.repository.WeatherRepositoryImpl
import com.chul.weather.data.source.local.WeatherLocalDataSource
import com.chul.weather.data.source.local.WeatherLocalDataSourceImpl
import com.chul.weather.data.source.local.room.WeatherDao
import com.chul.weather.data.source.local.room.WeatherDatabase
import com.chul.weather.data.source.remote.WeatherRemoteDataSource
import com.chul.weather.data.source.remote.WeatherRemoteDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherDatabase> {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }
    single<WeatherDao> { get<WeatherDatabase>().getWeatherDao() }

    single<WeatherLocalDataSource> { WeatherLocalDataSourceImpl(get()) }
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }

    single<WeatherRepositoryImpl> {
        WeatherRepositoryImpl(get(), get())
    }


}