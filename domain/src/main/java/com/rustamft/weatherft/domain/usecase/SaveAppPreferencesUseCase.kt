package com.rustamft.weatherft.domain.usecase

import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.repository.AppPreferencesRepository

class SaveAppPreferencesUseCase(
    private val preferencesRepository: AppPreferencesRepository
) {

    suspend fun execute(appPreferences: AppPreferences) {
        preferencesRepository.saveAppPreferences(appPreferences = appPreferences)
    }
}
