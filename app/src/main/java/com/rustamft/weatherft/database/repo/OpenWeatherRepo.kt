package com.rustamft.weatherft.database.repo

import android.util.Log
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.Weather
import com.rustamft.weatherft.util.EXCLUDE_WEATHER
import com.rustamft.weatherft.util.METRIC
import com.rustamft.weatherft.util.TAG_OPEN_WEATHER_REPO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class OpenWeatherRepo @Inject constructor(
    private val api: OpenWeatherApi
) : WeatherRepo {

    override suspend fun getCitiesList(city: String, apiKey: String): ArrayList<City> {
        return if (city == "" || apiKey == "") {
            ArrayList()
        } else {
            makeApiCall {
                api.getCitiesList(city, 5, apiKey)
            }.getOrThrow()
        }
    }

    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Weather {
        return makeApiCall {
            api.getWeather(
                lat,
                lon,
                EXCLUDE_WEATHER,
                METRIC,
                apiKey,
                Locale.getDefault().language
            )
        }.getOrThrow()
    }

    private suspend fun <T> makeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response: Response<T> = call()
                response.body()!!
            }.onFailure {
                Log.e(TAG_OPEN_WEATHER_REPO, it.message.toString())
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
