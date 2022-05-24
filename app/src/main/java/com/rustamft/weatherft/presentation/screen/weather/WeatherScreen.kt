package com.rustamft.weatherft.presentation.screen.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.util.PATTERN_DATE_TIME
import com.rustamft.weatherft.domain.util.PATTERN_TIME
import com.rustamft.weatherft.domain.util.ROUTE_WEATHER
import com.rustamft.weatherft.domain.util.TimeProvider
import com.rustamft.weatherft.presentation.activity.OnLifecycleEvent
import com.rustamft.weatherft.presentation.element.WeatherIconElement
import com.rustamft.weatherft.presentation.screen.destinations.LoginScreenDestination
import com.rustamft.weatherft.presentation.theme.AppTheme
import com.rustamft.weatherft.presentation.theme.DIMEN_BIG
import com.rustamft.weatherft.presentation.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.presentation.theme.DIMEN_SMALL
import com.rustamft.weatherft.presentation.theme.FONT_SIZE_BIG
import com.rustamft.weatherft.presentation.theme.FONT_SIZE_NORMAL
import com.rustamft.weatherft.presentation.theme.Shapes
import kotlin.math.roundToInt

@Destination(start = true, route = ROUTE_WEATHER)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState, // From DependenciesContainer.
    paddingValues: PaddingValues, // From DependenciesContainer.
    cityState: State<City> = viewModel.cityFlow.collectAsState(initial = City("...")),
    weatherState: State<Weather> = viewModel.weatherFlow.collectAsState(initial = Weather()),
) {

    val city by cityState
    val weather by weatherState
    val degrees = stringResource(R.string.degrees_centigrade)
    val scrollState = rememberScrollState()

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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    }"
                )
            }
        }
        WeatherIconElement(
            iconCode = weather.current.weather[0].icon,
            iconDescription = weather.current.weather[0].description,
            iconSize = DIMEN_BIG
        )
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${weather.current.temp}$degrees",
                    fontSize = FONT_SIZE_BIG
                )
                Text(
                    text = "${stringResource(R.string.feels_like)} ${weather.current.feels_like}$degrees"
                )
            }
        }
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${stringResource(R.string.wind)} ${
                        weather.current.wind_speed
                    } ${stringResource(R.string.meters_per_second)} ${
                        with(weather.current.wind_deg) {
                            val directions = listOf(
                                stringResource(id = R.string.wind_north),
                                stringResource(id = R.string.wind_northeast),
                                stringResource(id = R.string.wind_east),
                                stringResource(id = R.string.wind_southeast),
                                stringResource(id = R.string.wind_south),
                                stringResource(id = R.string.wind_southwest),
                                stringResource(id = R.string.wind_west),
                                stringResource(id = R.string.wind_northwest)
                            )
                            var count = (this * 8 / 360f).roundToInt()
                            count = (count + 8) % 8
                            directions[count]
                        }
                    }",
                )
                Text(
                    text = "${stringResource(id = R.string.sunrise)} ${
                        TimeProvider.millisToString(
                            weather.current.sunrise * 1000L, PATTERN_TIME
                        )
                    }"
                )
                Text(
                    text = "${stringResource(id = R.string.sunset)} ${
                        TimeProvider.millisToString(
                            weather.current.sunset * 1000L, PATTERN_TIME
                        )
                    }"
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .background(AppTheme.colors.secondary)
        ) {
            itemsIndexed(weather.hourly.take(13)) { _: Int, hourly: Weather.Hourly ->
                Column(
                    modifier = Modifier.padding(DIMEN_SMALL),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = TimeProvider.millisToString(hourly.dt * 1000L, PATTERN_TIME))
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(DIMEN_SMALL),
                        elevation = DIMEN_SMALL,
                        shape = Shapes.large
                    ) {
                        Column(
                            modifier = Modifier.padding(DIMEN_SMALL),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            WeatherIconElement(
                                iconCode = hourly.weather[0].icon,
                                iconDescription = hourly.weather[0].description,
                                iconSize = DIMEN_MEDIUM
                            )
                            Spacer(modifier = Modifier.height(DIMEN_SMALL))
                            Text(text = "${hourly.temp}$degrees")
                            Text(text = "${hourly.wind_speed} ${stringResource(R.string.meters_per_second)} ${
                                with(hourly.wind_deg) {
                                    val directions = listOf(
                                        stringResource(id = R.string.wind_north),
                                        stringResource(id = R.string.wind_northeast),
                                        stringResource(id = R.string.wind_east),
                                        stringResource(id = R.string.wind_southeast),
                                        stringResource(id = R.string.wind_south),
                                        stringResource(id = R.string.wind_southwest),
                                        stringResource(id = R.string.wind_west),
                                        stringResource(id = R.string.wind_northwest)
                                    )
                                    var count = (this * 8 / 360f).roundToInt()
                                    count = (count + 8) % 8
                                    directions[count]
                                }
                            }")
                        }
                    }
                }
            }
        }
    }
}
