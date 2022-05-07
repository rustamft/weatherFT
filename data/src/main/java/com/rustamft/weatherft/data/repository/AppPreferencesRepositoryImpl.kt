package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.AppPreferencesStorage
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext

internal class AppPreferencesRepositoryImpl(
    private val appPreferencesStorage: AppPreferencesStorage
) : AppPreferencesRepository {

    override suspend fun saveAppPreferences(appPreferences: AppPreferences) {
        withContext(Dispatchers.IO) {
            appPreferencesStorage.save(appPreferencesData = appPreferences.convert())
        }
    }

    override fun getAppPreferences(): Flow<AppPreferences> {
        return appPreferencesStorage.get().transform {
            emit(it.convert())
        }
    }
}
