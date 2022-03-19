package com.rustamft.weatherft.database.repo

import android.util.Log
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

private const val TAG = "OpenWeatherRepo"

class OpenWeatherRepo @Inject constructor(
    private val api: OpenWeatherApi
) : Repo {

    override suspend fun getCurrentWeather(lat: String, lon: String): CurrentWeather {
        val response: Response<CurrentWeather> = withContext(Dispatchers.IO) {
            try {
                api.getCurrentWeather(
                    lat,
                    lon,
                    "minutely,hourly,daily,alerts",
                    "2eec8f5a4f744e3045b451249d7286f5"
                )
            } catch (e: IOException) {
                Log.e(TAG, "IOException, internet connection might have been lost.")
                throw e
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response.")
                throw e
            }
        }
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            Log.e(TAG, "Response is not successful.")
            throw Exception("Unknown exception.")
        }
    }

    override suspend fun getWeatherForecast(): WeatherForecast {
        TODO("Not yet implemented")
    }
}