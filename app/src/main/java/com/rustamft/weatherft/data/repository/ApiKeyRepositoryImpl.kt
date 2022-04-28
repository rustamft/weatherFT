package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.storage.ApiKeyStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private class ApiKeyRepositoryImpl @Inject constructor(
    private val apiKeyStorage: ApiKeyStorage
) : ApiKeyRepository {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ApiKeyRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindApiKeyRepository(storage: ApiKeyRepositoryImpl): ApiKeyRepository
    }

    override suspend fun saveApiKey(apiKey: ApiKey) {
        withContext(Dispatchers.IO) {
            val apiKeyData = apiKey.convertTo(ApiKeyData::class.java)
            apiKeyStorage.save(apiKeyData)
        }
    }

    override suspend fun getApiKey(): ApiKey {
        return withContext(Dispatchers.IO) {
            val apiKeyData = apiKeyStorage.get()
            apiKeyData.convertTo(ApiKey::class.java)
        }
    }
}
