package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.ExternalApi
import com.rustamft.weatherft.data.storage.WeatherStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.domain.util.EXCLUDE_WEATHER
import com.rustamft.weatherft.domain.util.UNITS_METRIC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

internal class WeatherRepositoryImpl(
    private val weatherStorage: WeatherStorage,
    private val apiWrapper: ExternalApi
) : WeatherRepository {

    override suspend fun saveWeatherToStorage(weather: Weather) {
        weatherStorage.save(weather.convert())
    }

    override fun getWeatherFromStorage(): Flow<Weather> = weatherStorage.get().map { it.convert() }

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    override suspend fun getWeatherFromApi(
        city: City,
        apiKey: ApiKey,
        language: String
    ): Weather {
        val weatherData = apiWrapper.getWeather(
            city.lat,
            city.lon,
            EXCLUDE_WEATHER,
            UNITS_METRIC,
            apiKey.value,
            language
        )
        return weatherData.convert()
    }
}
