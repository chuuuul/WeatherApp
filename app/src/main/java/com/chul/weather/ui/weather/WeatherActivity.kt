package com.chul.weather.ui.weather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.chul.weather.R
import com.chul.weather.databinding.ActivityWeatherBinding
import com.chul.weather.ui.weather.adapter.WeatherAdapter
import com.chul.weather.ui.weather.adapter.WeatherDividerDecoration
import com.chul.weather.util.NetworkCheckHelper
import com.chul.weather.util.backPressExitTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class WeatherActivity : AppCompatActivity() {

    private val vm: WeatherViewModel by viewModel()
    private val networkCheckHelper: NetworkCheckHelper by inject()

    private val compositeDisposable = CompositeDisposable()
    private val backPressSubject = BehaviorSubject.createDefault(0L)

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
        settingObserver()
        checkNetwork()
    }

    private fun settingObserver() {
        vm.getWeatherEvent.observe(this, Observer {
            it?.getContentIfNotHandled()?.apply {
                vm.getAllWeather()
            }
        })

        backPressSubject.buffer(2, 1)
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { Pair(it[0], it[1]) }
            .subscribe {
                if (it.second - it.first < backPressExitTime) {
                    super.onBackPressed()
                } else {
                    Toast.makeText(this, R.string.activity_back_press, Toast.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)
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
                .setMessage(R.string.activity_disconnect_network)
                .setCancelable(false)
                .setPositiveButton(
                    R.string.activity_dialog_confirm
                ) { _, _ ->
                    Log.d("_chul", "네트워크 미 연결 - 종료")
                    finish()
                }.show()
        } else {
            vm.networkStateOn()
        }
    }

    override fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}