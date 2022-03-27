package com.rustamft.weatherft.database.datastore

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.util.DATE_TIME_PATTERN
import java.text.SimpleDateFormat
import java.util.Locale

data class WeatherPrefs(
    val apiKey: String = "",
    val city: City = City(),
    val currentWeather: CurrentWeather = CurrentWeather(),
    val lastTimeUpdatedMillis: Long = 0L
) {

    val lastTimeUpdatedString by lazy {
        millisToString(lastTimeUpdatedMillis)
    }

    private fun millisToString(millis: Long): String {
        return if (millis == 0L) {
            ""
        } else {
            val formatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
            formatter.format(millis)
        }
    }
}