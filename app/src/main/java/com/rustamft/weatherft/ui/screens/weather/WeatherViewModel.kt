package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rustamft.weatherft.database.entity.CurrentWeather
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

    private var _currentWeather = getCurrentWeatherFromPrefs()
    val currentWeather by mutableStateOf(_currentWeather)

    fun updateCurrentWeather() {
        viewModelScope.launch {
            val coordinates = prefs.getCoordinates()
            val lat = coordinates.first
            val lon = coordinates.second
            _currentWeather = repo.getCurrentWeather(lat, lon, prefs.getApiKey())
        }
    }

    private fun getCurrentWeatherFromPrefs(): CurrentWeather {
        val json = prefs.getCurrentWeather()
        return if (json == "") {
            CurrentWeather()
        } else {
            Gson().fromJson(json, CurrentWeather::class.java)
        }
    }
}
