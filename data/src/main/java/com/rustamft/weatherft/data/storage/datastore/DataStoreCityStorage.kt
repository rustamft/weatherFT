package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.storage.CityStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DataStoreCityStorage(
    private val dataStore: DataStore<StoredPreferences>
) : CityStorage {

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
