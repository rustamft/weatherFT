package com.rustamft.weatherft.data.model

data class StoredPreferences(
    val apiKeyData: ApiKeyData = ApiKeyData(),
    val cityData: CityData = CityData(),
    val weatherData: WeatherData = WeatherData()
)
