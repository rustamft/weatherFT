package com.rustamft.weatherft.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination
import com.rustamft.weatherft.util.DegreesStringBuilder
import java.util.Locale

@Destination(start = true)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    if (viewModel.city == null) {
        navigator.navigate(LoginScreenDestination)
    } else {
        viewModel.updateData()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = viewModel.city?.getLocalName(Locale.getDefault().language) ?: "",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(9.dp))
                Button(onClick = {
                    viewModel.clearCity()
                    navigator.navigate(LoginScreenDestination)
                }) {
                    Text(text = "Change")
                }
            }
            Text(
                text = "Updated: " + viewModel.lastTimeUpdatedString,
                modifier = Modifier.offset(0.dp, 50.dp)
            )
        }
        Box(contentAlignment = Alignment.TopCenter) {
            Text(
                text = DegreesStringBuilder.celsius(viewModel.currentWeather.current.temp),
                fontSize = 50.sp
            )
            Text(
                text = "Feels like " +
                        DegreesStringBuilder.celsius(viewModel.currentWeather.current.feels_like),
                modifier = Modifier.offset(0.dp, 60.dp)
            )
        }
        Text(
            text = viewModel.currentWeather.current.weather[0].description,
            fontSize = 30.sp
        )
    }
}
