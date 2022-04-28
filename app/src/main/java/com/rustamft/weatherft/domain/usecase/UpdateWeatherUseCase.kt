package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import com.rustamft.weatherft.domain.repository.CityRepository
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.util.TimeProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateWeatherUseCase @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend fun execute() {
        val weather = weatherRepository.getWeatherFromStorage().first()
        val now = TimeProvider.getNowAsMillis()
        val weatherUpdatedAt = weather.current.dt * 1000L
        if (now - weatherUpdatedAt > TimeProvider.FIFTEEN_MINUTES) {
            val updatedWeather = weatherRepository.getWeatherFromApi(
                cityRepository.getCity().first(),
                apiKeyRepository.getApiKey()
            )
            weatherRepository.saveWeatherToStorage(updatedWeather)
        }
    }
}
