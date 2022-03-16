package com.rustamft.weatherft.database

import android.util.Log
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val TAG = "OpenWeatherRepo"

class OpenWeatherRepo : Repo {

    private val api: OpenWeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    override suspend fun updateCurrentWeather(weather: Flow<CurrentWeather>) {
        withContext(Dispatchers.IO) {
            try {
                val response: Response<CurrentWeather> = api.getCurrentWeather(
                    "56.84",
                    "60.64",
                    "minutely,hourly,daily,alerts",
                    "2eec8f5a4f744e3045b451249d7286f5"
                )
                if (response.isSuccessful && response.body() != null) {
                    weather.transform {
                        emit(response.body()!!)
                    }
                } else {
                    Log.e(TAG, "Response is not successful.")
                }
            } catch (e: IOException) {
                Log.e(TAG, "IOException, internet connection might have been lost.")
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response.")
            }
        }
    }

    override suspend fun getWeatherForecast(): WeatherForecast {
        TODO("Not yet implemented")
    }
}