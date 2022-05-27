package com.rustamft.weatherft.presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.usecase.GetApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.SaveApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.SaveAppPreferencesUseCase
import com.rustamft.weatherft.domain.usecase.SaveCityUseCase
import com.rustamft.weatherft.domain.usecase.SearchCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val saveAppPreferencesUseCase: SaveAppPreferencesUseCase,
    private val saveApiKeyUseCase: SaveApiKeyUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val getApiKeyUseCase: GetApiKeyUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()
    var apiKey by mutableStateOf("")
    var cityName by mutableStateOf("")
    var listOfCities by mutableStateOf(emptyList<City>())
        private set

    init {
        viewModelScope.launch {
            apiKey = getApiKeyUseCase.execute().first().value
        }
    }

    fun saveAppPreferences(appPreferences: AppPreferences) {
        viewModelScope.launch {
            saveAppPreferencesUseCase.execute(appPreferences = appPreferences)
        }
    }

    fun saveApiKey() {
        viewModelScope.launch {
            saveApiKeyUseCase.execute(apiKey = ApiKey(apiKey))
        }
    }

    fun saveCity(city: City) {
        viewModelScope.launch {
            saveCityUseCase.execute(city = city)
        }
    }

    fun updateListOfCities() {
        viewModelScope.launch {
            runCatching {
                listOfCities = searchCityUseCase.execute(
                    City(name = cityName),
                    ApiKey(value = apiKey)
                )
            }.onFailure {
                errorChannel.send(it.message.toString())
            }
        }
    }
}
