package com.rustamft.weatherft.database.repo

import android.util.Log
import androidx.datastore.core.DataStore
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import com.rustamft.weatherft.util.EXCLUDE_ALL
import com.rustamft.weatherft.util.METRIC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

private const val TAG = "OpenWeatherRepo"

class OpenWeatherRepo @Inject constructor(
    private val dataStore: DataStore<WeatherPrefs>,
    private val api: OpenWeatherApi
) : WeatherRepo {

    override suspend fun getCitiesList(city: String, apiKey: String): ArrayList<City> {
        return if (city == "" || apiKey == "") {
            ArrayList()
        } else {
            makeApiCall {
                api.getCitiesList(city, 5, apiKey)
            }
        }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): CurrentWeather {
        return makeApiCall {
            api.getCurrentWeather(
                lat,
                lon,
                EXCLUDE_ALL,
                METRIC,
                apiKey,
                Locale.getDefault().language
            )
        }
    }

    override suspend fun getWeatherForecast(): WeatherForecast {
        TODO("Not yet implemented")
    }

    private suspend fun <T> makeApiCall(call: suspend () -> Response<T>): T {
        return withContext(Dispatchers.IO) {
            val response: Response<T> = try {
                call()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, internet connection might have been lost.")
                throw e
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response.")
                throw e
            }
            return@withContext if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                Log.e(TAG, "Response is not successful.")
                throw Exception("Unknown exception.")
            }
        }
    }
}
