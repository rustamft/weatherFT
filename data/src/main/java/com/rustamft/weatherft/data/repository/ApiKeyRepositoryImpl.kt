package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.ApiKeyStorage
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ApiKeyRepositoryImpl(
    private val apiKeyStorage: ApiKeyStorage
) : ApiKeyRepository {

    override suspend fun saveApiKey(apiKey: ApiKey) {
        withContext(Dispatchers.IO) {
            apiKeyStorage.save(apiKey.convert())
        }
    }

    override fun getApiKey(): Flow<ApiKey> = apiKeyStorage.get().map { it.convert() }
}
