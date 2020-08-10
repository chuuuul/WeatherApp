package com.chul.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.chul.weather.data.repository.WeatherRepositoryImpl
import com.chul.weather.util.LOC_CODE_SEOUL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: WeatherRepositoryImpl) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    fun getWeather() {
        repository.getWeather(LOC_CODE_SEOUL,6)

            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("_chul", "데이터 넘어왔다 : $it")
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

}