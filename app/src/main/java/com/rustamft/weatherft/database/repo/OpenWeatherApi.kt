package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.entity.WeatherForecast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @Module
    @InstallIn(SingletonComponent::class)
    object OpenWeatherApiModule {

        @Provides
        fun provideOpenWeatherApi(): OpenWeatherApi {
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi::class.java)
        }
    }

    /// data/2.5/onecall?lat=56.84&lon=60.64&exclude=minutely,hourly,daily&appid=2eec8f5a4f744e3045b451249d7286f5
    @GET("/data/2.5/onecall")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): Response<CurrentWeather>

    @GET("")
    suspend fun getWeatherForecast(): Response<WeatherForecast>
}