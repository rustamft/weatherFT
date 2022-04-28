package com.rustamft.weatherft.presentation.screen.login

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.domain.model.ApiKey
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.usecase.GetApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.SaveApiKeyUseCase
import com.rustamft.weatherft.domain.usecase.SaveCityUseCase
import com.rustamft.weatherft.domain.usecase.SearchCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val saveApiKeyUseCase: SaveApiKeyUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val getApiKeyUseCase: GetApiKeyUseCase,
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    var apiKey by mutableStateOf("")
    var cityName by mutableStateOf("")
    var listOfCities by mutableStateOf(listOf<City>())
        private set

    init {
        viewModelScope.launch {
            apiKey = getApiKeyUseCase.execute().value
        }
    }

    fun saveApiKey() {
        viewModelScope.launch {
            saveApiKeyUseCase.execute(ApiKey(apiKey))
        }
    }

    fun saveCity(city: City) {
        viewModelScope.launch {
            saveCityUseCase.execute(city)
        }
    }

    fun updateListOfCities(scaffoldState: ScaffoldState) {
        viewModelScope.launch {
            kotlin.runCatching {
                listOfCities = searchCityUseCase.execute(
                    City(name = cityName),
                    ApiKey(apiKey)
                )
            }.onFailure {
                scaffoldState.snackbarHostState.showSnackbar(it.message.toString())
            }
        }
    }
}
