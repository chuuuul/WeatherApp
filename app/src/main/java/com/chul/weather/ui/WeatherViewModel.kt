package com.chul.weather.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chul.weather.data.model.CategoryInfo
import com.chul.weather.data.model.WeatherAdapterModel
import com.chul.weather.data.repository.WeatherRepositoryImpl
import com.chul.weather.util.LOC_CODE_LONDON
import com.chul.weather.util.LOC_CODE_SEOUL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

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
        getWeather()
    }

    private fun getWeather() {
        repository.getWeather(LOC_CODE_SEOUL, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    seoulWeatherList.add(WeatherAdapterModel(CategoryInfo("Seoul")))
                }
                .subscribe({
                    seoulWeatherList.add(WeatherAdapterModel(it))
                }, {
                    Log.e("_chul", "${it.message}")
                }, {
                    Log.d("_chul", "On Complete")
                    seoulWeatherList.sortWith(compareBy({ it.type }, { it.weatherInfo?.date }))
                    updateSubject.onNext(Unit)
                })
                .addTo(compositeDisposable)

        repository.getWeather(LOC_CODE_LONDON, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    londonWeatherList.add(WeatherAdapterModel(CategoryInfo("London")))
                }.delay(1000L, TimeUnit.MILLISECONDS, Schedulers.computation())
                .subscribe({
                    londonWeatherList.add(WeatherAdapterModel(it))
                }, {
                    Log.e("_chul", "${it.message}")
                }, {
                    Log.d("_chul", "On Complete")
                    londonWeatherList.sortWith(compareBy({ it.type }, { it.weatherInfo?.date }))
                    updateSubject.onNext(Unit)
                })
                .addTo(compositeDisposable)

        repository.getWeather(LOC_CODE_LONDON, 6)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    chicagoWeatherList.add(WeatherAdapterModel(CategoryInfo("Chicago")))
                }.delay(1000L, TimeUnit.MILLISECONDS, Schedulers.computation())
                .subscribe({
                    chicagoWeatherList.add(WeatherAdapterModel(it))
                }, {
                    Log.e("_chul", "${it.message}")
                }, {
                    Log.d("_chul", "On Complete")
                    chicagoWeatherList.sortWith(compareBy({ it.type }, { it.weatherInfo?.date }))
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
}