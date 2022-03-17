package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.entity.CurrentWeather
import com.rustamft.weatherft.database.repo.AppRepo
import com.rustamft.weatherft.database.repo.OpenWeatherRepo
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    var currentWeather by mutableStateOf(CurrentWeather())

    //val prefs: SharedPreferences()
    val repo: AppRepo = OpenWeatherRepo()


    fun getCurrentWeather(): State<CurrentWeather> {
        viewModelScope.launch {
            currentWeather = repo.getCurrentWeather()
        }
        return currentWeather
    }
}