package com.rustamft.weatherft.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.rustamft.weatherft.data.model.container.DataContainer
import com.rustamft.weatherft.data.repository.ApiKeyRepositoryImpl
import com.rustamft.weatherft.data.repository.AppPreferencesRepositoryImpl
import com.rustamft.weatherft.data.repository.CityRepositoryImpl
import com.rustamft.weatherft.data.repository.WeatherRepositoryImpl
import com.rustamft.weatherft.data.storage.ApiKeyStorage
import com.rustamft.weatherft.data.storage.AppPreferencesStorage
import com.rustamft.weatherft.data.storage.CityStorage
import com.rustamft.weatherft.data.storage.ExternalApi
import com.rustamft.weatherft.data.storage.WeatherStorage
import com.rustamft.weatherft.data.storage.api.OpenWeatherApi
import com.rustamft.weatherft.data.storage.api.OpenWeatherApiWrapper
import com.rustamft.weatherft.data.storage.datastore.DataStoreApiKeyStorage
import com.rustamft.weatherft.data.storage.datastore.DataStoreAppPreferencesStorage
import com.rustamft.weatherft.data.storage.datastore.DataStoreCityStorage
import com.rustamft.weatherft.data.storage.datastore.DataStoreWeatherStorage
import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import com.rustamft.weatherft.domain.repository.AppPreferencesRepository
import com.rustamft.weatherft.domain.repository.CityRepository
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.domain.util.STORED_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<DataContainer> {
        return DataStoreFactory.create(
            serializer = DataContainer.Serializer,
            produceFile = { context.dataStoreFile(STORED_PREFERENCES) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Provides
    @Singleton
    fun provideAppPreferencesStorage(dataStore: DataStore<DataContainer>): AppPreferencesStorage {
        return DataStoreAppPreferencesStorage(dataStore)
    }

    @Provides
    @Singleton
    fun provideAppPreferencesRepository(appPreferencesStorage: AppPreferencesStorage): AppPreferencesRepository {
        return AppPreferencesRepositoryImpl(appPreferencesStorage)
    }

    @Provides
    @Singleton
    fun provideApiKeyStorage(dataStore: DataStore<DataContainer>): ApiKeyStorage {
        return DataStoreApiKeyStorage(dataStore)
    }

    @Provides
    @Singleton
    fun provideApiKeyRepository(apiKeyStorage: ApiKeyStorage): ApiKeyRepository {
        return ApiKeyRepositoryImpl(apiKeyStorage)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiWrapper(openWeatherApi: OpenWeatherApi): ExternalApi {
        return OpenWeatherApiWrapper(openWeatherApi)
    }

    @Provides
    @Singleton
    fun provideCityStorage(dataStore: DataStore<DataContainer>): CityStorage {
        return DataStoreCityStorage(dataStore)
    }

    @Provides
    @Singleton
    fun provideCityRepository(cityStorage: CityStorage, apiWrapper: ExternalApi): CityRepository {
        return CityRepositoryImpl(cityStorage, apiWrapper)
    }

    @Provides
    @Singleton
    fun provideWeatherStorage(dataStore: DataStore<DataContainer>): WeatherStorage {
        return DataStoreWeatherStorage(dataStore)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherStorage: WeatherStorage,
        apiWrapper: ExternalApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherStorage, apiWrapper)
    }
}
