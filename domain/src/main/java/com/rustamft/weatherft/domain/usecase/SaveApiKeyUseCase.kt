package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository

class SaveApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) {

    suspend fun execute(apiKey: ApiKey) = apiKeyRepository.saveApiKey(apiKey)
}
