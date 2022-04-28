package com.rustamft.weatherft.domain.repository

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun saveWeatherToStorage(weather: Weather)
    fun getWeatherFromStorage(): Flow<Weather>
    suspend fun getWeatherFromApi(city: City, apiKey: ApiKey): Weather
}
