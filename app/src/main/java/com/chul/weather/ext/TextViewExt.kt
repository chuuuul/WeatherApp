package com.chul.weather.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chul.weather.R

@BindingAdapter("android:abbreviationToFullText")
fun TextView.abbreviationToFullText(abb: String?) {
    if (abb.isNullOrBlank()) {
        return
    }

    val stringInt = when (abb) {
        "sn" -> R.string.sn
        "sl" -> R.string.sl
        "h" -> R.string.h
        "t" -> R.string.t
        "hr" -> R.string.hr
        "lr" -> R.string.lr
        "s" -> R.string.s
        "hc" -> R.string.hc
        "lc" -> R.string.lc
        "c" -> R.string.c
        else -> R.string.c
    }

    this.text = this.context.getString(stringInt)
}