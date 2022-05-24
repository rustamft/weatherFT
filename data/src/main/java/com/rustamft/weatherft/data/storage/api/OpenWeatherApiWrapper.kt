package com.rustamft.weatherft.data.storage.api

import android.util.Log
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.data.storage.ExternalApi
import com.rustamft.weatherft.domain.util.TAG_OPEN_WEATHER_API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal class OpenWeatherApiWrapper(
    private val openWeatherApi: OpenWeatherApi
) : ExternalApi {

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    override suspend fun searchCity(
        cityName: String,
        apiKey: String
    ): List<CityData> {
        return if (cityName == "" || apiKey == "") {
            ArrayList()
        } else {
            makeApiCall {
                openWeatherApi.searchCity(cityName, 5, apiKey)
            }.getOrThrow()
        }
    }

    @Throws(
        IOException::class,
        NullPointerException::class,
        Exception::class
    )
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        exclude: String,
        units: String,
        apiKey: String,
        language: String
    ): WeatherData {
        return makeApiCall {
            openWeatherApi.getWeather(
                latitude,
                longitude,
                exclude,
                units,
                apiKey,
                language
            )
        }.getOrThrow()
    }

    private suspend fun <T> makeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            runCatching {
                withTimeout(10000) {
                    val response: Response<T> = call()
                    response.body()!!
                }
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
                throw Exception(throwable.message())
            }
            is NullPointerException -> {
                throw NullPointerException("Response is not successful.")
            }
            is TimeoutCancellationException -> {
                throw Exception("Connection timeout.")
            }
            else -> {
                throw Exception("Unknown exception.")
            }
        }
    }
}
