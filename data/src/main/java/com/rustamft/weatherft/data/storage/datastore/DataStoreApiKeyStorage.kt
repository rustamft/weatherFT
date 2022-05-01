package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.storage.ApiKeyStorage
import kotlinx.coroutines.flow.first

internal class DataStoreApiKeyStorage(
    private val dataStore: DataStore<StoredPreferences>
) : ApiKeyStorage {

    override suspend fun save(apiKeyData: ApiKeyData) {
        dataStore.updateData {
            it.copy(apiKeyData = apiKeyData)
        }
    }

    override suspend fun get(): ApiKeyData {
        return dataStore.data.first().apiKeyData
    }
}
