package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.storage.ApiWrapper
import com.rustamft.weatherft.data.storage.CityStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private class CityRepositoryImpl @Inject constructor(
    private val cityStorage: CityStorage,
    private val api: ApiWrapper
) : CityRepository {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class CityRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindCityRepository(storage: CityRepositoryImpl): CityRepository
    }

    override suspend fun saveCity(city: City) {
        withContext(Dispatchers.IO) {
            val cityData = city.convertTo(CityData::class.java)
            cityStorage.save(cityData)
        }
    }

    override fun getCity(): Flow<City> {
        return cityStorage.get().transform {
            val city = it.convertTo(City::class.java)
            emit(city)
        }
    }

    override suspend fun searchCity(city: City, apiKey: ApiKey): List<City> {
        return withContext(Dispatchers.IO) {
            val cityData = city.convertTo(CityData::class.java)
            val apiKeyData = apiKey.convertTo(ApiKeyData::class.java)
            val listOfCityData = api.searchCity(cityData, apiKeyData)
            listOfCityData.map { it.convertTo(City::class.java) }
        }
    }
}
