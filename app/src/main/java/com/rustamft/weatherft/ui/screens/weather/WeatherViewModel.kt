package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.prefs.SharedPrefs
import com.rustamft.weatherft.database.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val prefs: SharedPrefs,
    private val repo: Repo
) : ViewModel() {

    val city = prefs.getCity()

    var currentWeather by mutableStateOf(prefs.getCurrentWeather())

    fun clearCity() = prefs.clearCity()

    fun updateCurrentWeather() {
        viewModelScope.launch {
            if (city != null) {
                currentWeather = repo.getCurrentWeather(
                    city.lat,
                    city.lon,
                    prefs.getApiKey()
                )
                prefs.setCurrentWeather(currentWeather)
            }
        }
    }
}
