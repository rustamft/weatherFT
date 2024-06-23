package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.model.container.DataContainer
import com.rustamft.weatherft.data.storage.WeatherStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataStoreWeatherStorage(
    private val dataStore: DataStore<DataContainer>
) : WeatherStorage {

    override suspend fun save(weatherData: WeatherData) {
        dataStore.updateData {
            it.copy(weatherData = weatherData)
        }
    }

    override fun get(): Flow<WeatherData> = dataStore.data.map { it.weatherData }
}
