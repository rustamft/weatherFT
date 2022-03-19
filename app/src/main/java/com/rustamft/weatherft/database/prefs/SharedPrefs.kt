package com.rustamft.weatherft.database.prefs

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

    fun setCoordinates(lat: String, lon: String)
    fun getCoordinates(): Pair<String, String>
    fun getCurrentWeather(): String
}