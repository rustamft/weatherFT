package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.WeatherData
import kotlinx.coroutines.flow.Flow

internal interface WeatherStorage {

    suspend fun save(weatherData: WeatherData)
    fun get(): Flow<WeatherData>
}
