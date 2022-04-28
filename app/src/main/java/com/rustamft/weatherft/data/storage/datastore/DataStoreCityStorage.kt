package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.storage.CityStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

private class DataStoreCityStorage @Inject constructor(
    private val dataStore: DataStore<StoredPreferences>
) : CityStorage {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class CityStorageModule {
        @Binds
        @Singleton
        abstract fun bindCityStorage(storage: DataStoreCityStorage): CityStorage
    }

    override suspend fun save(cityData: CityData) {
        dataStore.updateData {
            it.copy(cityData = cityData)
        }
    }

    override fun get(): Flow<CityData> {
        return dataStore.data.transform {
            emit(it.cityData)
        }
    }
}
