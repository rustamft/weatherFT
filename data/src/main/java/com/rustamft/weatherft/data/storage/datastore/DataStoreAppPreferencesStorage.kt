package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.AppPreferencesData
import com.rustamft.weatherft.data.model.DataContainer
import com.rustamft.weatherft.data.storage.AppPreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataStoreAppPreferencesStorage(
    private val dataStore: DataStore<DataContainer>
) : AppPreferencesStorage {

    override suspend fun save(appPreferencesData: AppPreferencesData) {
        dataStore.updateData {
            it.copy(appPreferencesData = appPreferencesData)
        }
    }

    override fun get(): Flow<AppPreferencesData> = dataStore.data.map { it.appPreferencesData }
}
