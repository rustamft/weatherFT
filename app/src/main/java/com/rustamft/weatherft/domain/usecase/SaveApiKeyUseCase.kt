package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import javax.inject.Inject

class SaveApiKeyUseCase @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository
) {

    suspend fun execute(apiKey: ApiKey) {
        apiKeyRepository.saveApiKey(apiKey)
    }
}
