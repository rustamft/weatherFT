package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository

class SearchCityUseCase(
    private val cityRepository: CityRepository
) {

    suspend fun execute(city: City, apiKey: ApiKey): List<City> {
        return cityRepository.searchCity(city, apiKey)
    }
}
