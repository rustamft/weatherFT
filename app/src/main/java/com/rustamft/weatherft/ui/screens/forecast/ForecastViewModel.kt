package com.rustamft.weatherft.ui.screens.forecast

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    dataStore: DataStore<WeatherPrefs>
) : ViewModel() {

    val prefsFlow = dataStore.data

}
