package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData

interface ApiWrapper {

    suspend fun searchCity(city: CityData, apiKey: ApiKeyData): List<CityData>
    suspend fun getWeather(city: CityData, apiKey: ApiKeyData): WeatherData
}
