package com.rustamft.weatherft.database.repo

import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.Weather
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

    // https://api.openweathermap.org/geo/1.0/direct?q=Kamensk&appid=
    @GET("/geo/1.0/direct")
    suspend fun getCitiesList(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String,
    ): Response<ArrayList<City>>

    // https://api.openweathermap.org/data/2.5/onecall?lat=56.84&lon=60.64&exclude=minutely,hourly,alerts&appid=
    @GET("/data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
        @Query("lang") language: String
    ): Response<Weather>
}
