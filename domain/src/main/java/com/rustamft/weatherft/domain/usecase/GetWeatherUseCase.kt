package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {

    fun execute(): Flow<Weather> = weatherRepository.getWeatherFromStorage()
}
