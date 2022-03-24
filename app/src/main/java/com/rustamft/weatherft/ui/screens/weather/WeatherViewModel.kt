package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.prefs.SharedPrefs
import com.rustamft.weatherft.database.repo.Repo
import com.rustamft.weatherft.util.DATE_TIME_PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val prefs: SharedPrefs,
    private val repo: Repo
) : ViewModel() {

    val city = prefs.getCity()

    private var lastTimeUpdatedMillis = prefs.getLastTimeUpdated()
    var lastTimeUpdatedString by mutableStateOf(millisToString(lastTimeUpdatedMillis))

    var currentWeather by mutableStateOf(prefs.getCurrentWeather())

    fun clearCity() = prefs.clearCity()

    fun updateData() {
        val now = Calendar.getInstance().timeInMillis
        val fifteenMinutes = 15L * 60L * 1000L
        if (
            city != null &&
            now - lastTimeUpdatedMillis > fifteenMinutes
        ) {
            viewModelScope.launch {
                currentWeather = repo.getCurrentWeather(
                    city.lat,
                    city.lon,
                    prefs.getApiKey()
                )
                prefs.setCurrentWeather(currentWeather)
                prefs.setLastTimeUpdated(now)
                lastTimeUpdatedMillis = now
                lastTimeUpdatedString = millisToString(lastTimeUpdatedMillis)
            }
        }
    }

    private fun millisToString(millis: Long): String {
        return if (millis == 0L) {
            ""
        } else {
            val formatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
            formatter.format(millis)
        }
    }
}
