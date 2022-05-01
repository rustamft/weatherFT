package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.ApiKeyData

internal interface ApiKeyStorage {

    suspend fun save(apiKeyData: ApiKeyData)
    suspend fun get(): ApiKeyData
}
