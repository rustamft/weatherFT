package com.rustamft.weatherft.domain.repository

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun saveCity(city: City)
    fun getCity(): Flow<City>
    suspend fun searchCity(city: City, apiKey: ApiKey): List<City>
}
