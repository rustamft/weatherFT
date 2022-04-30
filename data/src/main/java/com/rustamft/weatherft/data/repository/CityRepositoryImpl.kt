package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.ApiWrapper
import com.rustamft.weatherft.data.storage.CityStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext

class CityRepositoryImpl(
    private val cityStorage: CityStorage,
    private val api: ApiWrapper
) : CityRepository {

    override suspend fun saveCity(city: City) {
        withContext(Dispatchers.IO) {
            cityStorage.save(city.convert())
        }
    }

    override fun getCity(): Flow<City> {
        return cityStorage.get().transform {
            emit(it.convert())
        }
    }

    override suspend fun searchCity(city: City, apiKey: ApiKey): List<City> {
        return withContext(Dispatchers.IO) {
            val listOfCityData = api.searchCity(city.name, apiKey.value)
            listOfCityData.map { it.convert() }
        }
    }
}
