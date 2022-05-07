package com.rustamft.weatherft.data.storage

import com.rustamft.weatherft.data.model.AppPreferencesData
import kotlinx.coroutines.flow.Flow

internal interface AppPreferencesStorage {

    suspend fun save(appPreferencesData: AppPreferencesData)
    fun get(): Flow<AppPreferencesData>
}
