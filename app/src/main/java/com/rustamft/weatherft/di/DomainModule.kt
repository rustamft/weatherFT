package com.rustamft.weatherft.di

import com.rustamft.weatherft.domain.repository.ApiKeyRepository
import com.rustamft.weatherft.domain.repository.CityRepository
import com.rustamft.weatherft.domain.repository.WeatherRepository
import com.rustamft.weatherft.domain.usecase.GetApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.GetCityUseCase
import com.rustamft.weatherft.domain.usecase.GetWeatherUseCase
import com.rustamft.weatherft.domain.usecase.SaveApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.SaveCityUseCase
import com.rustamft.weatherft.domain.usecase.SearchCityUseCase
import com.rustamft.weatherft.domain.usecase.UpdateWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetApiKeyUseCase(apiKeyRepository: ApiKeyRepository): GetApiKeyUseCase {
        return GetApiKeyUseCase(apiKeyRepository)
    }

    @Provides
    fun provideGetCityUseCase(cityRepository: CityRepository): GetCityUseCase {
        return GetCityUseCase(cityRepository)
    }

    @Provides
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepository)
    }

    @Provides
    fun provideSaveApiKeyUseCase(apiKeyRepository: ApiKeyRepository): SaveApiKeyUseCase {
        return SaveApiKeyUseCase(apiKeyRepository)
    }

    @Provides
    fun provideSaveCityUseCase(cityRepository: CityRepository): SaveCityUseCase {
        return SaveCityUseCase(cityRepository)
    }

    @Provides
    fun provideSearchCityUseCase(cityRepository: CityRepository): SearchCityUseCase {
        return SearchCityUseCase(cityRepository)
    }

    @Provides
    fun provideUpdateWeatherUseCase(
        apiKeyRepository: ApiKeyRepository,
        weatherRepository: WeatherRepository
    ): UpdateWeatherUseCase {
        return UpdateWeatherUseCase(apiKeyRepository, weatherRepository)
    }
}
