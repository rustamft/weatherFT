package com.rustamft.weatherft.database

import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun updateCurrentWeather(weather: Flow<CurrentWeather>)
    suspend fun getWeatherForecast(): WeatherForecast
}