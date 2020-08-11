package com.chul.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chul.weather.data.model.CategoryInfo
import com.chul.weather.data.model.WeatherAdapterModel
import com.chul.weather.data.repository.WeatherRepositoryImpl
import com.chul.weather.util.LOC_CODE_CHICAGO
import com.chul.weather.util.LOC_CODE_LONDON
import com.chul.weather.util.LOC_CODE_SEOUL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class WeatherViewModel(private val repository: WeatherRepositoryImpl) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val seoulWeatherList = mutableListOf<WeatherAdapterModel>()
    private val londonWeatherList = mutableListOf<WeatherAdapterModel>()
    private val chicagoWeatherList = mutableListOf<WeatherAdapterModel>()

    private val _weatherAdapterModelList = MutableLiveData<MutableList<WeatherAdapterModel>>(mutableListOf())
    val weatherAdapterModelList: LiveData<MutableList<WeatherAdapterModel>> = _weatherAdapterModelList

    private val updateSubject = PublishSubject.create<Unit>()

    init {
        adapterUpdateObserve()
        getAllWeather(6)
    }

    private fun getAllWeather(duration: Int) {
        getWeather(LOC_CODE_SEOUL, duration)
        getWeather(LOC_CODE_LONDON, duration)
        getWeather(LOC_CODE_CHICAGO, duration)
    }

    private fun getWeather(locationCode: String, duration: Int) {
        val title: String
        val list: List<WeatherAdapterModel>

        when (locationCode) {
            LOC_CODE_SEOUL -> {
                title = "Seoul"
                list = seoulWeatherList
            }
            LOC_CODE_LONDON -> {
                title = "London"
                list = londonWeatherList
            }
            else -> {
                title = "Chicago"
                list = chicagoWeatherList
            }
        }

        repository.getWeather(locationCode, duration)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    list.add(WeatherAdapterModel(CategoryInfo(title)))
                }
                .subscribe({
                    list.add(WeatherAdapterModel(it))
                }, {
                    Log.e("_chul", "${it.message}")
                }, {
                    Log.d("_chul", "On Complete : $title")
                    list.sortWith(compareBy({ it.type }, { it.weatherInfo?.date }))
                    updateSubject.onNext(Unit)
                })
                .addTo(compositeDisposable)
    }

    private fun adapterUpdateObserve() {
        updateSubject.buffer(3, 3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val mutableList = mutableListOf<WeatherAdapterModel>()
                    mutableList.addAll(seoulWeatherList)
                    mutableList.addAll(londonWeatherList)
                    mutableList.addAll(chicagoWeatherList)
                    _weatherAdapterModelList.value = mutableList
                }.addTo(compositeDisposable)
    }

    private fun unbindViewModel() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unbindViewModel()
        super.onCleared()
    }
}