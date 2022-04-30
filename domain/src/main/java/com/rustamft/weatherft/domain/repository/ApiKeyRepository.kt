package com.rustamft.weatherft.domain.repository

import com.rustamft.weatherft.domain.model.ApiKey

interface ApiKeyRepository {

    suspend fun saveApiKey(apiKey: ApiKey)
    suspend fun getApiKey(): ApiKey
}
