package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.domain.util.TimeProvider

class UpdateWeatherUseCase(
    private val apiKeyRepository: ApiKeyRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute(
        city: City,
        weather: Weather,
        language: String
    ) {
        val now = TimeProvider.getNowAsMillis()
        val weatherUpdatedAt = weather.current.dt * 1000L
        if (now - weatherUpdatedAt > TimeProvider.FIFTEEN_MINUTES) {
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
