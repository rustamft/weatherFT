package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.CityStorage
import com.rustamft.weatherft.data.storage.ExternalApi
import com.rustamft.weatherft.data.util.convert
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

internal class CityRepositoryImpl(
    private val cityStorage: CityStorage,
    private val api: ExternalApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CityRepository {

    override suspend fun saveCity(city: City) {
        withContext(dispatcher) {
            cityStorage.save(cityData = city.convert())
        }
    }

    override fun getCity(): Flow<City> = cityStorage.get().map { it.convert() }

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    override suspend fun searchCity(city: City, apiKey: ApiKey): List<City> {
        return withContext(dispatcher) {
            val listOfCityData = api.searchCity(cityName = city.name, apiKey = apiKey.value)
            listOfCityData.map { it.convert() }
        }
    }
}
