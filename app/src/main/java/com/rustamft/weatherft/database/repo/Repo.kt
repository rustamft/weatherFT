package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.CitiesList
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

    suspend fun getCitiesList(city: String, apiKey: String): CitiesList

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        apiKey: String
    ): CurrentWeather

    suspend fun getWeatherForecast(): WeatherForecast
}
