package com.rustamft.weatherft.domain.repository

import com.rustamft.weatherft.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    suspend fun saveAppPreferences(appPreferences: AppPreferences)
    fun getAppPreferences(): Flow<AppPreferences>
}
