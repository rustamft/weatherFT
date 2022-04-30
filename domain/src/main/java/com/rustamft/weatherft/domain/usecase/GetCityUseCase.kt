package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

class GetCityUseCase(
    private val cityRepository: CityRepository
) {

    fun execute(): Flow<City> = cityRepository.getCity()
}
