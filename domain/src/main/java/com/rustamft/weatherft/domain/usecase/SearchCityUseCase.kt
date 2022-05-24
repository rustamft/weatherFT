package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import java.io.IOException

class SearchCityUseCase(
    private val cityRepository: CityRepository
) {

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    suspend fun execute(city: City, apiKey: ApiKey): List<City> {
        return cityRepository.searchCity(city, apiKey)
    }
}
