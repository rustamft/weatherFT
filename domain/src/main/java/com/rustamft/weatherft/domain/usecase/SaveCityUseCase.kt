package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository

class SaveCityUseCase(
    private val cityRepository: CityRepository
) {

    suspend fun execute(city: City) {
        cityRepository.saveCity(city)
    }
}
