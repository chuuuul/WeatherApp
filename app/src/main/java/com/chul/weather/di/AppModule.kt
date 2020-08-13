package com.chul.weather.di

import com.chul.weather.util.NetworkCheckHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<NetworkCheckHelper> { NetworkCheckHelper(androidContext()) }
}