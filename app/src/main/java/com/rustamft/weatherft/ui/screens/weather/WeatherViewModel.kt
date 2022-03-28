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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
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
                    val now = Calendar.getInstance().timeInMillis
                    val fifteenMinutes = 15L * 60L * 1000L
                    if (now - lastTimeUpdatedMillis > fifteenMinutes) {
                        val updatedCurrentWeather = repo.getCurrentWeather(
                            city.lat,
                            city.lon,
                            apiKey
                        )
                        val updatedWeatherPrefs = copy(
                            currentWeather = updatedCurrentWeather,
                            lastTimeUpdatedMillis = now
                        )
                        dataStore.setWeatherPrefs(updatedWeatherPrefs)
                    }
                }
            }
        }
    }
}
