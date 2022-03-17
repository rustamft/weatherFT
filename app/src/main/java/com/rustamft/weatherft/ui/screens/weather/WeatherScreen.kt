package com.rustamft.weatherft.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator
) {

    val weather = viewModel.currentWeather
    var timezone by remember {
        mutableStateOf("")
    }

    LaunchedEffect(weather) {
        weather.collect {
            timezone = it.timezone
        }
    }
    viewModel.updateCurrentWeather()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = timezone)
    }
}