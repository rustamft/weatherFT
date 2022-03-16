package com.rustamft.weatherft.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.database.entity.WeatherForecast
import com.rustamft.weatherft.ui.destinations.WeatherScreenDestination
import com.rustamft.weatherft.ui.theme.WeatherFTTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherFTTheme {
        //WeatherScreen()
    }
}

@Destination(start = true)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = MainViewModel()
) {

    var text by remember {
        mutableStateOf("")
    }
    Column {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text("City")
            }
        )
        Spacer(modifier = Modifier.height(9.dp))
        Button(onClick = {
            navigator.navigate(
                WeatherScreenDestination()
            )
        }) {
            Text(text = "Proceed")
        }
    }

}

@Destination
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
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

@Destination
@Composable
fun ForecastScreen(
    navigator: DestinationsNavigator,
    forecast: WeatherForecast
) {
    // TODO
}