package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.Weather
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface WeatherRepo {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepoModule {
        @Binds
        @Singleton
        abstract fun bindRepo(sharedPrefs: OpenWeatherRepo): WeatherRepo
    }

    suspend fun getCitiesList(city: String, apiKey: String): ArrayList<City>

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Weather
}
