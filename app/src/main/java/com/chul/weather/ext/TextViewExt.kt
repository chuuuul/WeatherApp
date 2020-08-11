package com.chul.weather.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chul.weather.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

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

@BindingAdapter("android:dateToAbbreviation")
fun TextView.dateToAbbreviation(localDate: LocalDate?) {
    if (localDate == null) {
        return
    }

    val today = LocalDate.now()
    val dateDiff = ChronoUnit.DAYS.between(today, localDate).toInt()

    val abb = when (dateDiff) {
        0 -> "Today"
        1 -> "Tomorrow"
        else -> {
            localDate.format(DateTimeFormatter.ofPattern("E d MMM", Locale.ENGLISH))
        }
    }
    this.text = abb.toString()
}