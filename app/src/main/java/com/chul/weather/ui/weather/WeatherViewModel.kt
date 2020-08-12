package com.chul.weather.ui.weather

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

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _weatherAdapterModelList =
        MutableLiveData<MutableList<WeatherAdapterModel>>(mutableListOf())
    val weatherAdapterModelList: LiveData<MutableList<WeatherAdapterModel>> =
        _weatherAdapterModelList

    private val updateSubject = PublishSubject.create<Unit>()

    init {
        adapterUpdateObserve(3)
        getAllWeather(6)
    }

    private fun getAllWeather(duration: Int) {
        getWeather(LOC_CODE_SEOUL, duration)
        getWeather(LOC_CODE_LONDON, duration)
        getWeather(LOC_CODE_CHICAGO, duration)
    }

    private fun getWeather(locationCode: String, duration: Int) {
        val title: String
        val list: MutableList<WeatherAdapterModel>

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
                setLoadingVisible(true)
                list.clear()
                list.add(WeatherAdapterModel(CategoryInfo(title)))
            }.doAfterSuccess {
                Log.d("_chul", "After Success : $title")
                updateSubject.onNext(Unit)
            }.subscribe({ weatherInfoList ->
                weatherInfoList.forEach { weatherInfo ->
                    list.add(WeatherAdapterModel(weatherInfo))
                }
            }, {
                Log.e("_chul", "${it.message}")
            })
            .addTo(compositeDisposable)
    }

    private fun adapterUpdateObserve(bufferCount: Int) {
        updateSubject.buffer(bufferCount, bufferCount)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterNext { setLoadingVisible(false) }
            .subscribe {
                val mutableList = mutableListOf<WeatherAdapterModel>()
                mutableList.addAll(seoulWeatherList)
                mutableList.addAll(londonWeatherList)
                mutableList.addAll(chicagoWeatherList)
                _weatherAdapterModelList.value = mutableList
            }.addTo(compositeDisposable)
    }

    private fun setLoadingVisible(visible: Boolean) {
        if (_isLoading.value != visible) {
            _isLoading.value = visible
        }
    }

    private fun unbindViewModel() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unbindViewModel()
        super.onCleared()
    }
}