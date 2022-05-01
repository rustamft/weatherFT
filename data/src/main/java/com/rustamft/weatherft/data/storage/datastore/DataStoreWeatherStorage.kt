package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.storage.WeatherStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

internal class DataStoreWeatherStorage(
    private val dataStore: DataStore<StoredPreferences>
) : WeatherStorage {

    override suspend fun save(weatherData: WeatherData) {
        dataStore.updateData {
            it.copy(weatherData = weatherData)
        }
    }

    override fun get(): Flow<WeatherData> {
        return dataStore.data.transform {
            emit(it.weatherData)
        }
    }
}
