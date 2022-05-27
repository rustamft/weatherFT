package com.rustamft.weatherft.domain.repository

import com.rustamft.weatherft.domain.model.ApiKey
import kotlinx.coroutines.flow.Flow

interface ApiKeyRepository {

    suspend fun saveApiKey(apiKey: ApiKey)
    fun getApiKey(): Flow<ApiKey>
}
