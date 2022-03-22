package com.rustamft.weatherft.database.prefs

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface SharedPrefs {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class SharedPrefsModule {
        @Binds
        @Singleton
        abstract fun bindSharedPrefs(sharedPrefs: WeatherSharedPrefs): SharedPrefs
    }

    fun setApiKey(key: String)
    fun getApiKey(): String
    fun setCity(city: City)
    fun getCity(): City?
    fun getCurrentWeather(): CurrentWeather
}
