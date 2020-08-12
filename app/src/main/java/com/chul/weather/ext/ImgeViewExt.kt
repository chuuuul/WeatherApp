package com.chul.weather.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chul.weather.R

@BindingAdapter("android:abbreviationToImage")
fun ImageView.abbreviationToImage(abb: String?) {
    if (abb == null) {
        return
    }
    val url = "https://www.metaweather.com/static/img/weather/png/64/$abb.png"

    Glide.with(this)
        .load(url.ifBlank { R.drawable.ic_baseline_not_interested_24 })
        .placeholder(R.drawable.ic_baseline_android_24)
        .error(R.drawable.ic_baseline_not_interested_24)
        .into(this)
}