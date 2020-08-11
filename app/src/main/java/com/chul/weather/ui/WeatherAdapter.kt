package com.chul.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chul.weather.R
import com.chul.weather.data.model.CategoryInfo
import com.chul.weather.data.model.WeatherAdapterModel
import com.chul.weather.data.model.WeatherInfo
import com.chul.weather.databinding.ItemCategoryBinding
import com.chul.weather.databinding.ItemWeatherBinding

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val weatherList = mutableListOf<WeatherAdapterModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            WeatherAdapterModel.category -> {
                val dataBinding: ItemCategoryBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_category, parent, false
                )
                CategoryViewHolder(dataBinding)
            }
            else -> {

                val dataBinding: ItemWeatherBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_weather, parent, false
                )
                WeatherViewHolder(dataBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return weatherList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (weatherList[position].type) {
            WeatherAdapterModel.category -> {
                weatherList[position].categoryInfo?.let { categoryInfo ->
                    (holder as CategoryViewHolder).onBind(categoryInfo)
                }
            }

            WeatherAdapterModel.weather -> {
                weatherList[position].weatherInfo?.let { weatherInfo ->
                    (holder as WeatherViewHolder).onBind(weatherInfo)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return weatherList[position].type
    }

    fun addNewItem(newList: Collection<WeatherAdapterModel>) {
        weatherList.clear()
        weatherList.addAll(newList)
        notifyDataSetChanged()
    }
}

class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun onBind(weather: WeatherInfo) {
        binding.weatherInfo = weather
        binding.executePendingBindings()
    }
}

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun onBind(category: CategoryInfo) {
        binding.categoryInfo = category
    }
}


