package com.rustamft.weatherft.data.storage.api

import android.util.Log
import com.rustamft.weatherft.app.appLanguage
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.storage.ApiWrapper
import com.rustamft.weatherft.util.EXCLUDE_WEATHER
import com.rustamft.weatherft.util.METRIC
import com.rustamft.weatherft.util.TAG_OPEN_WEATHER_API
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private class OpenWeatherApiWrapper @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : ApiWrapper {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ApiWrapperModule {
        @Binds
        @Singleton
        abstract fun bindApiWrapper(storage: OpenWeatherApiWrapper): ApiWrapper
    }

    override suspend fun searchCity(
        city: CityData,
        apiKey: ApiKeyData
    ): List<CityData> {
        return if (city.name == "" || apiKey.value == "") {
            ArrayList()
        } else {
            makeApiCall {
                openWeatherApi.searchCity(city.name, 5, apiKey.value)
            }.getOrThrow()
        }
    }

    override suspend fun getWeather(
        city: CityData,
        apiKey: ApiKeyData
    ): WeatherData {
        return makeApiCall {
            openWeatherApi.getWeather(
                city.lat,
                city.lon,
                EXCLUDE_WEATHER,
                METRIC,
                apiKey.value,
                appLanguage
            )
        }.getOrThrow()
    }

    private suspend fun <T> makeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response: Response<T> = call()
                response.body()!!
            }.onFailure {
                Log.e(TAG_OPEN_WEATHER_API, it.message.toString())
                rethrow(it)
            }
        }
    }

    private fun rethrow(throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                throw IOException("IOException, internet connection might have been lost.")
            }
            is HttpException -> {
                throw HttpException(throwable.response()!!)
            }
            is NullPointerException -> {
                throw NullPointerException("Response is not successful.")
            }
            else -> {
                throw Exception("Unknown exception.")
            }
        }
    }
}
