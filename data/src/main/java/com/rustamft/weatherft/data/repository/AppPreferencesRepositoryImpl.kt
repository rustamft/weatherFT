package com.rustamft.weatherft.data.repository

import com.rustamft.weatherft.data.storage.AppPreferencesStorage
import com.rustamft.weatherft.data.util.convert
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class AppPreferencesRepositoryImpl(
    private val appPreferencesStorage: AppPreferencesStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppPreferencesRepository {

    override suspend fun saveAppPreferences(appPreferences: AppPreferences) {
        withContext(dispatcher) {
            appPreferencesStorage.save(appPreferencesData = appPreferences.convert())
        }
    }

    override fun getAppPreferences(): Flow<AppPreferences> =
        appPreferencesStorage.get().map { it.convert() }
}
