package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository

class GetApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) {

    suspend fun execute(): ApiKey = apiKeyRepository.getApiKey()
}
