package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import com.rustamft.weatherft.domain.repository.CityRepository
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.domain.util.TimeProvider
import com.rustamft.weatherft.domain.util.round
import kotlinx.coroutines.flow.first
import java.io.IOException

class UpdateWeatherUseCase(
    private val apiKeyRepository: ApiKeyRepository,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    suspend fun execute(language: String) {
        val city = cityRepository.getCity().first()
        val weather = weatherRepository.getWeatherFromStorage().first()
        val isUpdateNeeded = city.lat.round(2) != weather.lat.round(2) ||
                city.lon.round(2) != weather.lon.round(2) ||
                TimeProvider.getNowAsMillis() - weather.current.dt * 1000L >
                TimeProvider.FIFTEEN_MINUTES
        if (isUpdateNeeded) {
            val apiKey = apiKeyRepository.getApiKey()
            val updatedWeather = weatherRepository.getWeatherFromApi(
                city,
                apiKey,
                language
            )
            weatherRepository.saveWeatherToStorage(updatedWeather)
        }
    }
}
