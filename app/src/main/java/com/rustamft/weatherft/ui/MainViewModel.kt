package com.rustamft.weatherft.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.OpenWeatherRepo
import com.rustamft.weatherft.database.Repo
import com.rustamft.weatherft.database.entity.CurrentWeather
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val currentWeather = emptyFlow<CurrentWeather>()

    //val prefs: SharedPreferences()
    val repo: Repo = OpenWeatherRepo()

    fun updateCurrentWeather() {
        viewModelScope.launch {
            repo.updateCurrentWeather(currentWeather)
        }
    }
}