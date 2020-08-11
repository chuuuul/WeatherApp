package com.chul.weather.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chul.weather.data.model.WeatherAdapterModel
import com.chul.weather.ui.weather.adapter.WeatherAdapter

@BindingAdapter("android:items")
fun RecyclerView.setAdapter(items: MutableList<WeatherAdapterModel>?) {
    if (items == null) {
        return
    }

    when (this.adapter) {
        is WeatherAdapter -> {
            val adapter = this.adapter as WeatherAdapter
            adapter.addNewItem(items)
        }
        else -> return
    }
}