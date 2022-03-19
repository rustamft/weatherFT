package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination

@Destination(start = true)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherViewModel = viewModel()
) {

    if (viewModel.currentWeather.timezone == "") {
        navigator.navigate(LoginScreenDestination)
    } else {
        viewModel.updateCurrentWeather()
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = viewModel.currentWeather.timezone)
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    //WeatherScreen()
}