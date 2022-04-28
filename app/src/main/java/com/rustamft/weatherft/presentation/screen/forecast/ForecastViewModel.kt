package com.rustamft.weatherft.presentation.screen.forecast

import androidx.lifecycle.ViewModel
import com.rustamft.weatherft.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val weatherFlow = getWeatherUseCase.execute()
}
