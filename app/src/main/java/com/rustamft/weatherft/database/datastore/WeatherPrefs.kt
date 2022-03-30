package com.rustamft.weatherft.database.datastore

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather

data class WeatherPrefs(
    val apiKey: String = "",
    val city: City = City(),
    val currentWeather: CurrentWeather = CurrentWeather(),
    val lastTimeUpdated: Long = 0L
)
