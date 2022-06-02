package com.rustamft.weatherft.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rustamft.weatherft.data.model.ApiKeyData
import com.rustamft.weatherft.data.model.AppPreferencesData
import com.rustamft.weatherft.data.model.CityData
import com.rustamft.weatherft.data.model.WeatherData
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather

internal fun AppPreferences.convert(): AppPreferencesData {
    return AppPreferencesData(darkTheme = this.darkTheme)
}

internal fun AppPreferencesData.convert(): AppPreferences {
    return AppPreferences(darkTheme = this.darkTheme)
}

internal fun ApiKey.convert(): ApiKeyData {
    return ApiKeyData(value = this.value)
}

internal fun ApiKeyData.convert(): ApiKey {
    return ApiKey(value = this.value)
}

internal fun City.convert(): CityData {
    val gson = Gson()
    val json = gson.toJson(this)
    val cityData = gson.fromJson(json, CityData::class.java)
    cityData.local_names = this.localNames.fromMap(CityData.LocalNames::class.java)
    return cityData
}

internal fun CityData.convert(): City {
    val gson = Gson()
    val json = gson.toJson(this)
    val city = gson.fromJson(json, City::class.java)
    city.localNames = this.local_names.toMap()
    return city
}

internal fun Weather.convert(): WeatherData {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, WeatherData::class.java)
}

internal fun WeatherData.convert(): Weather {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, Weather::class.java)
}

private fun <T> Map<String, String>.fromMap(type: Class<T>): T {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, type)
}

private fun <T> T.toMap(): Map<String, String> {
    return convert()
}

private inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}
