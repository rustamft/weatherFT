package com.rustamft.weatherft.database.datastore

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.Weather

data class WeatherPrefs(
    val apiKey: String = "",
    val city: City = City(),
    val weather: Weather = Weather(),
    val lastTimeUpdated: Long = 0L
)
