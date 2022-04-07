package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.database.datastore.setWeatherPrefs
import com.rustamft.weatherft.database.repo.WeatherRepo
import com.rustamft.weatherft.util.TimeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dataStore: DataStore<WeatherPrefs>,
    private val repo: WeatherRepo
) : ViewModel() {

    val prefsFlow = dataStore.data
    var prefsAreEmpty by mutableStateOf(false)

    fun updateData() {
        viewModelScope.launch {
            val weatherPrefs = prefsFlow.first()
            if (weatherPrefs.apiKey == "" || weatherPrefs.city.name == "") {
                prefsAreEmpty = true
            } else {
                with(weatherPrefs) {
                    val now = TimeProvider.getNowAsMillis()
                    if (now - lastTimeUpdated > TimeProvider.FIFTEEN_MINUTES) {
                        val updatedCurrentWeather = repo.getWeather(
                            city.lat,
                            city.lon,
                            apiKey
                        )
                        val updatedWeatherPrefs = copy(
                            weather = updatedCurrentWeather,
                            lastTimeUpdated = now
                        )
                        dataStore.setWeatherPrefs(updatedWeatherPrefs)
                    }
                }
            }
        }
    }
}
