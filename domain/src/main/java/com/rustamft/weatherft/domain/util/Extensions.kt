package com.rustamft.weatherft.domain.util

import com.rustamft.weatherft.domain.model.DateTime
import java.util.Calendar
import java.util.Locale

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Long.asDateTime(): DateTime {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val date = "${
        String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
    }.${
        String.format("%02d", calendar.get(Calendar.MONTH))
    }.${
        calendar.get(Calendar.YEAR)
    }"
    val time = "${
        String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
    }:${
        String.format("%02d", calendar.get(Calendar.MINUTE))
    }"
    val dayOfWeek = calendar.getDisplayName(
        Calendar.DAY_OF_WEEK,
        Calendar.LONG,
        Locale.getDefault()
    )

    return DateTime(
        date = date,
        time = time,
        dayOfWeek = dayOfWeek
    )
}
