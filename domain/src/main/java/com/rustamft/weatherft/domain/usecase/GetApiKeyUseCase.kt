package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import kotlinx.coroutines.flow.Flow

class GetApiKeyUseCase(
    private val apiKeyRepository: ApiKeyRepository
) {

    fun execute(): Flow<ApiKey> = apiKeyRepository.getApiKey()
}
