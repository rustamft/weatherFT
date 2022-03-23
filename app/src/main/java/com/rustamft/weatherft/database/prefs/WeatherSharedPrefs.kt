package com.rustamft.weatherft.database.prefs

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.util.API_KEY
import com.rustamft.weatherft.util.CITY
import com.rustamft.weatherft.util.CURRENT_WEATHER
import com.rustamft.weatherft.util.SHARED_PREFS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val TAG = "WeatherSharedPrefs"

class WeatherSharedPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) : SharedPrefs {

    private val prefs =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun setApiKey(key: String) {
        prefs
            .edit()
            .putString(API_KEY, key)
            .apply()
    }

    override fun getApiKey(): String {
        return prefs.getString(API_KEY, "") ?: ""
    }

    override fun clearApiKey() {
        prefs
            .edit()
            .remove(API_KEY)
            .apply()
    }

    override fun setCity(city: City) {
        val json = gson.toJson(city)
        prefs
            .edit()
            .putString(CITY, json)
            .apply()
    }

    override fun getCity(): City? {
        val json = prefs.getString(CITY, "") ?: ""
        return try {
            gson.fromJson(json, City::class.java)
        } catch (e: JsonSyntaxException) {
            Log.d(TAG, "JSON syntax is not valid. City could not be restored from prefs.")
            null
        }
    }

    override fun clearCity() {
        prefs
            .edit()
            .remove(CITY)
            .apply()
    }

    override fun setCurrentWeather(currentWeather: CurrentWeather) {
        val json = gson.toJson(currentWeather)
        prefs
            .edit()
            .putString(CURRENT_WEATHER, json)
            .apply()
    }

    override fun getCurrentWeather(): CurrentWeather {
        val json = prefs.getString(CURRENT_WEATHER, "") ?: ""
        return if (json == "") {
            CurrentWeather()
        } else {
            Gson().fromJson(json, CurrentWeather::class.java)
        }
    }

    // https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android
}
