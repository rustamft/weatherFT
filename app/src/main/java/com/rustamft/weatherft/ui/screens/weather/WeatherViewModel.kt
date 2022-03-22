package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

    private var _currentWeather = prefs.getCurrentWeather()
    val currentWeather by mutableStateOf(_currentWeather)

    fun updateCurrentWeather() {
        viewModelScope.launch {
            val city = prefs.getCity()
            if (city != null) {
                _currentWeather = repo.getCurrentWeather(
                    city.lat,
                    city.lon,
                    prefs.getApiKey()
                )
            }
        }
    }
}
