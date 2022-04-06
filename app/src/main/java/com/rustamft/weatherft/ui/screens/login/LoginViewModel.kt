package com.rustamft.weatherft.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.database.datastore.setApiKey
import com.rustamft.weatherft.database.datastore.setCity
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: DataStore<WeatherPrefs>,
    private val repo: WeatherRepo
) : ViewModel() {

    val prefsFlow = dataStore.data
    var listOfCities by mutableStateOf(listOf<City>())
        private set

    fun setCity(city: City) {
        viewModelScope.launch {
            dataStore.setCity(city)
        }
    }

    fun updateListOfCities(cityName: String, apiKey: String) {
        viewModelScope.launch {
            listOfCities = repo.getCitiesList(cityName, apiKey)
        }
    }

    fun setApiKey(apiKey: String) {
        viewModelScope.launch {
            dataStore.setApiKey(apiKey)
        }
    }
}
