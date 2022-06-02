package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.container.DataContainer
import com.rustamft.weatherft.data.storage.CityStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataStoreCityStorage(
    private val dataStore: DataStore<DataContainer>
) : CityStorage {

    override suspend fun save(cityData: CityData) {
        dataStore.updateData {
            it.copy(cityData = cityData)
        }
    }

    override fun get(): Flow<CityData> = dataStore.data.map { it.cityData }
}
