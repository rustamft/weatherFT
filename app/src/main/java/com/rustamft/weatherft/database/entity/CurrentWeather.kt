package com.rustamft.weatherft.database.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CurrentWeather(
    val current: @RawValue Current,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
) : Parcelable {

    constructor() : this(
        Current(
            0,
            0.0,
            0,
            0.0,
            0,
            0,
            0,
            0,
            0.0,
            0,
            0,
            listOf(
                Weather(
                    "",
                    "",
                    0,
                    ""
                )
            ),
            0,
            0
        ),
        0.0,
        0.0,
        "",
        0
    )
}

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_speed: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
