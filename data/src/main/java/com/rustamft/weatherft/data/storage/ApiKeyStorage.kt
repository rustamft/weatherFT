package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.ApiKeyData
import kotlinx.coroutines.flow.Flow

internal interface ApiKeyStorage {

    suspend fun save(apiKeyData: ApiKeyData)
    fun get(): Flow<ApiKeyData>
}
