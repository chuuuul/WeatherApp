package com.chul.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chul.weather.R
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.getWeather()
    }
}