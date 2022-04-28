package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.storage.ApiWrapper
import com.rustamft.weatherft.data.storage.WeatherStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

private class WeatherRepositoryImpl @Inject constructor(
    private val weatherStorage: WeatherStorage,
    private val apiWrapper: ApiWrapper
) : WeatherRepository {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class WeatherRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindWeatherRepository(storage: WeatherRepositoryImpl): WeatherRepository
    }

    override suspend fun saveWeatherToStorage(weather: Weather) {
        weatherStorage.save(weather.convertTo(WeatherData::class.java))
    }

    override fun getWeatherFromStorage(): Flow<Weather> {
        return weatherStorage.get().transform {
            val weather = it.convertTo(Weather::class.java)
            emit(weather)
        }
    }

    override suspend fun getWeatherFromApi(city: City, apiKey: ApiKey): Weather {
        val cityData = city.convertTo(CityData::class.java)
        val apiKeyData = apiKey.convertTo(ApiKeyData::class.java)
        val weatherData = apiWrapper.getWeather(cityData, apiKeyData)
        return weatherData.convertTo(Weather::class.java)
    }
}
