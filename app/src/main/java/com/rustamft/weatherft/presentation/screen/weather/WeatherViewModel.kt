package com.rustamft.weatherft.presentation.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.usecase.GetCityUseCase
import com.rustamft.weatherft.domain.usecase.GetWeatherUseCase
import com.rustamft.weatherft.domain.usecase.UpdateWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getCityUseCase: GetCityUseCase,
    getWeatherUseCase: GetWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase
) : ViewModel() {

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()
    val cityFlow = getCityUseCase.execute()
    val weatherFlow = getWeatherUseCase.execute()

    fun updateWeather() {
        viewModelScope.launch {
            runCatching {
                updateWeatherUseCase.execute(App.language)
            }.onFailure {
                errorChannel.send(it.message.toString())
            }
        }
    }
}
