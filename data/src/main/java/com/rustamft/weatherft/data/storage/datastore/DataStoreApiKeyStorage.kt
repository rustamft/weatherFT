package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.DataContainer
import com.rustamft.weatherft.data.storage.ApiKeyStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DataStoreApiKeyStorage(
    private val dataStore: DataStore<DataContainer>
) : ApiKeyStorage {

    override suspend fun save(apiKeyData: ApiKeyData) {
        dataStore.updateData {
            it.copy(apiKeyData = apiKeyData)
        }
    }

    override fun get(): Flow<ApiKeyData> = dataStore.data.map { it.apiKeyData }
}
