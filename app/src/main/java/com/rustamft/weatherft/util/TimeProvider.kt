package com.rustamft.weatherft.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeProvider {

    const val FIFTEEN_MINUTES = 15L * 60L * 1000L

    fun getNowAsMillis(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun millisToString(millis: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(
            millis
        )
    }
}
