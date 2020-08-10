package com.chul.weather

import android.app.Application
import com.chul.weather.di.networkModule
import com.chul.weather.di.repositoryModule
import com.chul.weather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@WeatherApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }

    }
}