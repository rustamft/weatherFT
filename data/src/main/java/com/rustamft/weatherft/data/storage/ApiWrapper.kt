package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData

interface ApiWrapper {

    suspend fun searchCity(
        cityName: String,
        apiKey: String
    ): List<CityData>

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        exclude: String,
        units: String,
        apiKey: String,
        language: String
    ): WeatherData
}
