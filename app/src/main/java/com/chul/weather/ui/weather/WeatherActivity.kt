package com.chul.weather.ui.weather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.chul.weather.R
import com.chul.weather.databinding.ActivityWeatherBinding
import com.chul.weather.ui.weather.adapter.WeatherAdapter
import com.chul.weather.ui.weather.adapter.WeatherDividerDecoration
import com.chul.weather.util.NetworkCheckHelper
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class WeatherActivity : AppCompatActivity() {

    private val vm: WeatherViewModel by viewModel()
    private val networkCheckHelper: NetworkCheckHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)

        binding.lifecycleOwner = this
        binding.vm = vm

        settingInit()
    }

    private fun settingInit() {
        settingAdapter()
        settingObserve()
        checkNetwork()
    }

    private fun settingObserve() {
        vm.getWeatherEvent.observe(this, Observer {
            it?.getContentIfNotHandled()?.apply {
                vm.getAllWeather()
            }
        })
    }


    private fun settingAdapter() {
        rv_weather_list.adapter =
            WeatherAdapter()
        rv_weather_list.addItemDecoration(
            WeatherDividerDecoration(
                this
            )
        )
    }

    private fun checkNetwork() {
        if (!networkCheckHelper.isInternetAvailable()) {
            AlertDialog.Builder(this)
                .setMessage("인터넷이 연결되어 있지 않습니다.")
                .setCancelable(false)
                .setPositiveButton(
                    "확인"
                ) { _, _ ->
                    Log.d("_chul", "네트워크 미 연결 - 종료")
                    finish()
                }.show()
        } else {
            vm.networkStateOn()
        }
    }
}