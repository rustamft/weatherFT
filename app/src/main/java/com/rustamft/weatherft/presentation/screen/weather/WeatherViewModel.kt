package com.rustamft.weatherft.presentation.screen.weather

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.usecase.GetCityUseCase
import com.rustamft.weatherft.domain.usecase.GetWeatherUseCase
import com.rustamft.weatherft.domain.usecase.UpdateWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getCityUseCase: GetCityUseCase,
    getWeatherUseCase: GetWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase
) : ViewModel() {

    val cityFlow = getCityUseCase.execute()
    val weatherFlow = getWeatherUseCase.execute()

    fun updateWeather(scaffoldState: ScaffoldState) {
        viewModelScope.launch {
            runCatching {
                updateWeatherUseCase.execute(App.language)
            }.onFailure {
                scaffoldState.snackbarHostState.showSnackbar(it.message.toString())
            }
        }
    }
}
