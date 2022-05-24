package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.ExternalApi
import com.rustamft.weatherft.data.storage.CityStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import java.io.IOException

internal class CityRepositoryImpl(
    private val cityStorage: CityStorage,
    private val api: ExternalApi
) : CityRepository {

    override suspend fun saveCity(city: City) {
        withContext(Dispatchers.IO) {
            cityStorage.save(cityData = city.convert())
        }
    }

    override fun getCity(): Flow<City> {
        return cityStorage.get().transform {
            emit(it.convert())
        }
    }

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    override suspend fun searchCity(city: City, apiKey: ApiKey): List<City> {
        return withContext(Dispatchers.IO) {
            val listOfCityData = api.searchCity(cityName = city.name, apiKey = apiKey.value)
            listOfCityData.map { it.convert() }
        }
    }
}
