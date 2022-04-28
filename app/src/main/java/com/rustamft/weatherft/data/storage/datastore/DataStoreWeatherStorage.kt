package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.storage.WeatherStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

class DataStoreWeatherStorage @Inject constructor(
    private val dataStore: DataStore<StoredPreferences>
) : WeatherStorage {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class WeatherStorageModule {
        @Binds
        @Singleton
        abstract fun bindWeatherStorage(storage: DataStoreWeatherStorage): WeatherStorage
    }

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
