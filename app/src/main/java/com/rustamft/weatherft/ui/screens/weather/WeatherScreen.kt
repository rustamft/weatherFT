package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val degreeText by lazy {
        val degree = viewModel.currentWeather.current.temp
        var text = viewModel.currentWeather.current.temp.toString() + "°C"
        if (degree > 0.0f) {
            text = "+$text"
        }
        text
    }

    if (viewModel.city == null) {
        navigator.navigate(LoginScreenDestination)
    } else {
        viewModel.updateCurrentWeather()
        cityLocalName = viewModel.city.getLocalName(Locale.getDefault().language)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = cityLocalName)
            Spacer(modifier = Modifier.width(9.dp))
            Button(onClick = {
                viewModel.clearCity()
                navigator.navigate(LoginScreenDestination)
            }) {
                Text(text = "Change")
            }
        }
        Text(text = degreeText)
    }
}
