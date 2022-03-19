package com.rustamft.weatherft.database.prefs

import android.content.Context
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.util.CURRENT_WEATHER
import com.rustamft.weatherft.util.LATITUDE
import com.rustamft.weatherft.util.LONGITUDE
import com.rustamft.weatherft.util.SHARED_PREFS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherSharedPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) : SharedPrefs {

    private val prefs =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun setCoordinates(lat: String, lon: String) {
        prefs
            .edit()
            .putString(LATITUDE, lat)
            .putString(LONGITUDE, lon)
            .apply()
    }

    override fun getCoordinates(): Pair<String, String> {
        val lat = prefs.getString(LATITUDE, "51.49") ?: "51.49"
        val lon = prefs.getString(LONGITUDE, "00.00") ?: "00.00"
        return Pair(lat, lon)
    }

    override fun getCurrentWeather(): String {
        return prefs.getString(CURRENT_WEATHER, "") ?: ""
    }

    // https://stackoverflow.com/questions/7145606/how-do-you-save-store-objects-in-sharedpreferences-on-android
}