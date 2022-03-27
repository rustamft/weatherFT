package com.rustamft.weatherft.ui.screens.forecast

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.database.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val dataStore: DataStore<WeatherPrefs>,
    private val repo: WeatherRepo
) : ViewModel()
