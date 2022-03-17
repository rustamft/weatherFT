package com.rustamft.weatherft.database.repo

import android.util.Log
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import com.rustamft.weatherft.database.repo.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val TAG = "OpenWeatherRepo"

class OpenWeatherRepo : AppRepo {

    private val api: OpenWeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    override suspend fun getCurrentWeather(): CurrentWeather {
        val response: Response<CurrentWeather> = withContext(Dispatchers.IO) {
            try {
                api.getCurrentWeather(
                    "56.84",
                    "60.64",
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