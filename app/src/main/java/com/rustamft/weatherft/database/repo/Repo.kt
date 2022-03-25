package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.CitiesList
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface Repo {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepoModule {
        @Binds
        @Singleton
        abstract fun bindRepo(sharedPrefs: OpenWeatherRepo): Repo
    }

    suspend fun getCitiesList(city: String, apiKey: String): ArrayList<City>

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): CurrentWeather

    suspend fun getWeatherForecast(): WeatherForecast
}
