package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {

    fun execute(): Flow<City> = cityRepository.getCity()
}
