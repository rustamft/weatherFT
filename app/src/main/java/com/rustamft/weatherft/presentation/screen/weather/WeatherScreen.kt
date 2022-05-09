package com.rustamft.weatherft.presentation.screen.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.util.PATTERN_DATE_TIME
import com.rustamft.weatherft.domain.util.PATTERN_TIME
import com.rustamft.weatherft.domain.util.ROUTE_WEATHER
import com.rustamft.weatherft.domain.util.TimeProvider
import com.rustamft.weatherft.presentation.activity.OnLifecycleEvent
import com.rustamft.weatherft.presentation.screen.destinations.LoginScreenDestination
import com.rustamft.weatherft.presentation.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.presentation.theme.FONT_SIZE_BIG
import com.rustamft.weatherft.presentation.theme.FONT_SIZE_NORMAL

@Destination(start = true, route = ROUTE_WEATHER)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState, // From DependenciesContainer.
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val degrees = stringResource(R.string.degrees_centigrade)

    val city by viewModel.cityFlow.collectAsState(
        initial = com.rustamft.weatherft.domain.model.City("...")
    )
    val weather by viewModel.weatherFlow.collectAsState(
        initial = Weather()
    )

    if (city.name == "") {
        navigator.navigate(LoginScreenDestination)
    } else if (city.name != "...") {
        OnLifecycleEvent { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.updateWeather(scaffoldState)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Text(
                text = city.localNames[App.language] ?: city.name,
                fontSize = FONT_SIZE_NORMAL
            )
            Text(
                text = "${stringResource(R.string.updated_at)} ${
                    TimeProvider.millisToString(
                        weather.current.dt * 1000L,
                        PATTERN_DATE_TIME
                    )
                }",
                modifier = Modifier.offset(0.dp, 40.dp)
            )
        }
        Box(contentAlignment = Alignment.TopCenter) {
            Text(
                text = "${weather.current.temp}$degrees",
                fontSize = FONT_SIZE_BIG
            )
            Text(
                text = "${stringResource(R.string.feels_like)} ${weather.current.feels_like}$degrees",
                modifier = Modifier.offset(0.dp, 60.dp)
            )
        }
        Text(
            text = weather.current.weather[0].description,
            fontSize = FONT_SIZE_NORMAL
        )
        Text(
            text = "${stringResource(R.string.wind)} ${weather.current.wind_speed} ${
                stringResource(R.string.meters_per_second)
            }"
        )
        LazyRow(modifier = Modifier.background(MaterialTheme.colors.secondary)) {
            itemsIndexed(weather.hourly.take(24)) { index: Int, hourly: Weather.Hourly ->
                if (index < 24) {
                    Column(
                        modifier = Modifier.padding(DIMEN_MEDIUM),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = TimeProvider.millisToString(hourly.dt * 1000L, PATTERN_TIME))
                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(DIMEN_MEDIUM),
                            elevation = DIMEN_MEDIUM
                        ) {
                            Column(
                                modifier = Modifier.padding(DIMEN_MEDIUM),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "${hourly.temp}$degrees")
                                Text(text = hourly.weather[0].description)
                            }
                        }
                    }
                }
            }
        }
    }
}
