package com.rustamft.weatherft.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.database.prefs.SharedPrefs
import com.rustamft.weatherft.database.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val prefs: SharedPrefs,
    private val repo: Repo
) : ViewModel() {

    var listOfCities by mutableStateOf(listOf<City>())
    var isApiKeyEntered by mutableStateOf(prefs.getApiKey() != "")

    fun setApiKey(key: String) {
        prefs.setApiKey(key)
        isApiKeyEntered = key != ""
    }

    fun clearApiKey() {
        prefs.clearApiKey()
        isApiKeyEntered = false
    }

    fun setCity(city: City) = prefs.setCity(city)

    fun updateCitiesList(city: String) {
        viewModelScope.launch {
            listOfCities = repo.getCitiesList(city, prefs.getApiKey())
        }
    }
}
