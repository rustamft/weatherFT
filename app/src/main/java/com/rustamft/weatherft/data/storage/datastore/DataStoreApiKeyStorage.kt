package com.rustamft.weatherft.data.storage.datastore

import androidx.datastore.core.DataStore
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.StoredPreferences
import com.rustamft.weatherft.data.storage.ApiKeyStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private class DataStoreApiKeyStorage @Inject constructor(
    private val dataStore: DataStore<StoredPreferences>
) : ApiKeyStorage {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ApiKeyStorageModule {
        @Binds
        @Singleton
        abstract fun bindApiKeyStorage(storage: DataStoreApiKeyStorage): ApiKeyStorage
    }

    override suspend fun save(apiKeyData: ApiKeyData) {
        dataStore.updateData {
            it.copy(apiKeyData = apiKeyData)
        }
    }

    override suspend fun get(): ApiKeyData {
        return dataStore.data.first().apiKeyData
    }
}
