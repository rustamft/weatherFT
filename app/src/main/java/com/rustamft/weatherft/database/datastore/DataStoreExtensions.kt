package com.rustamft.weatherft.database.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.Weather

suspend fun DataStore<WeatherPrefs>.setWeatherPrefs(weatherPrefs: WeatherPrefs) {
    updateData {
        weatherPrefs
    }
}

suspend fun DataStore<WeatherPrefs>.setApiKey(apiKey: String) {
    updateData {
        it.copy(apiKey = apiKey)
    }
}

suspend fun DataStore<WeatherPrefs>.setCity(city: City) {
    updateData {
        it.copy(
            city = city,
            weather = Weather(),
            lastTimeUpdated = 0L
        )
    }
}
