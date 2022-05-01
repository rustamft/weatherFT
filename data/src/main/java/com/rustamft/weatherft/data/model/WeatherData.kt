package com.rustamft.weatherft.data.model

internal data class WeatherData(
    val current: Current = Current(),
    val daily: List<Daily> = listOf(Daily()),
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val timezone: String = "",
    val timezone_offset: Int = 0
) {

    data class Current(
        val dt: Int = 0,
        val temp: Double = 0.0,
        val feels_like: Double = 0.0,
        val weather: List<Description> = listOf(Description()),
        val clouds: Int = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0,
        val dew_point: Double = 0.0,
        val humidity: Int = 0,
        val pressure: Int = 0,
        val uvi: Double = 0.0,
        val visibility: Int = 0,
        val wind_deg: Int = 0,
        val wind_speed: Double = 0.0
    )

    data class Daily(
        val dt: Int = 0,
        val temp: Temp = Temp(),
        val feels_like: FeelsLike = FeelsLike(),
        val weather: List<Description> = listOf(Description()),
        val sunrise: Int = 0,
        val sunset: Int = 0,
        val moonrise: Int = 0,
        val moonset: Int = 0,
        val moon_phase: Double = 0.0,
        val clouds: Int = 0,
        val dew_point: Double = 0.0,
        val humidity: Int = 0,
        val pop: Double = 0.0,
        val pressure: Int = 0,
        val rain: Double = 0.0,
        val snow: Double = 0.0,
        val uvi: Double = 0.0,
        val wind_deg: Int = 0,
        val wind_gust: Double = 0.0,
        val wind_speed: Double = 0.0
    )

    data class Description(
        val id: Int = 0,
        val main: String = "",
        val description: String = "",
        val icon: String = ""
    )

    data class FeelsLike(
        val day: Double = 0.0,
        val eve: Double = 0.0,
        val morn: Double = 0.0,
        val night: Double = 0.0,
    )

    data class Temp(
        val max: Double = 0.0,
        val min: Double = 0.0,
        val day: Double = 0.0,
        val eve: Double = 0.0,
        val morn: Double = 0.0,
        val night: Double = 0.0,
    )
}
