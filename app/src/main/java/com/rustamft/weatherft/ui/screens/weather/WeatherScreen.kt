package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination
import java.util.Locale

@Destination(start = true)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    var cityLocalName by remember {
        mutableStateOf("")
    }

    if (viewModel.city == null) {
        navigator.navigate(LoginScreenDestination)
    } else {
        viewModel.updateCurrentWeather()
        cityLocalName = viewModel.city.getLocalName(Locale.getDefault().language)
        // TODO: crash
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = cityLocalName)
    }
}
