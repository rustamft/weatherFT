package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    fun execute(): Flow<Weather> = weatherRepository.getWeatherFromStorage()
}
