package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetAppPreferencesUseCase(
    private val preferencesRepository: AppPreferencesRepository
) {

    fun execute(): Flow<AppPreferences> = preferencesRepository.getAppPreferences()
}
