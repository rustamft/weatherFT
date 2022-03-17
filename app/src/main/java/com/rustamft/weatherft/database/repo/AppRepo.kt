package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast

interface AppRepo {

    suspend fun getCurrentWeather(): CurrentWeather
    suspend fun getWeatherForecast(): WeatherForecast
}