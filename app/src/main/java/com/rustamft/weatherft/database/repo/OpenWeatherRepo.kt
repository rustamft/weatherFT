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
            val result = makeApiCall {
                api.getCitiesList(city, 5, apiKey)
            }
            result.getOrThrow()
        }
    }

    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Weather {
        val result = makeApiCall {
            api.getWeather(
                lat,
                lon,
                EXCLUDE_WEATHER,
                METRIC,
                apiKey,
                Locale.getDefault().language
            )
        }
        return result.getOrThrow()
    }

    private suspend fun <T> makeApiCall(call: suspend () -> Response<T>): Result<T> {
        val result = withContext(Dispatchers.IO) {
            runCatching {
                val response: Response<T> = call()
                response.body()!!
            }
        }
        result.onFailure {
            val message = when (it) {
                is IOException -> {
                    "IOException, internet connection might have been lost."
                }
                is HttpException -> {
                    "HttpException, unexpected response."
                }
                is NullPointerException -> {
                    "Response is not successful."
                }
                else -> {
                    "Unknown exception."
                }
            }
            Log.e(TAG_OPEN_WEATHER_REPO, message)
        }
        return result
    }
}
