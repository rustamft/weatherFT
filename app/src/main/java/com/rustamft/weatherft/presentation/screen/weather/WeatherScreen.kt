package com.rustamft.weatherft.presentation.screen.weather

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.util.ROUTE_WEATHER
import com.rustamft.weatherft.domain.util.asDateTime
import com.rustamft.weatherft.presentation.activity.OnLifecycleEvent
import com.rustamft.weatherft.presentation.element.SwitchElement
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
    cityState: State<City> = viewModel.cityFlow.collectAsState(initial = City("...")),
    weatherState: State<Weather> = viewModel.weatherFlow.collectAsState(initial = Weather()),
) {

    val city by cityState
    val weather by weatherState

    if (city.name == "") {
        navigator.navigate(LoginScreenDestination)
    } else if (city.name != "...") {
        LaunchedEffect(key1 = scaffoldState) {
            viewModel.errorFlow.collect { error ->
                scaffoldState.snackbarHostState.showSnackbar(
                    message = error
                )
            }
        }
        OnLifecycleEvent { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.updateWeather()
            }
        }
    }

    WeatherScreenContent(
        city = city,
        weather = weather
    )
}

@Composable
fun WeatherScreenContent(
    city: City,
    weather: Weather,
    scrollState: ScrollState = rememberScrollState()
) {

    val degreesName = stringResource(R.string.degrees_centigrade)
    var dailyForecastOn by remember { mutableStateOf(false) }

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
                val updatedAtDateTime = (weather.current.dt * 1000L).asDateTime()
                Text(
                    text = "${stringResource(R.string.updated_at)} ${
                        updatedAtDateTime.date
                    } ${
                        updatedAtDateTime.time
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
                    text = "${weather.current.temp}$degreesName",
                    fontSize = FONT_SIZE_BIG
                )
                Text(
                    text = "${stringResource(R.string.feels_like)} ${weather.current.feels_like}$degreesName"
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
                val sunriseDateTime = (weather.current.sunrise * 1000L).asDateTime()
                Text(
                    text = "${stringResource(id = R.string.sunrise)} ${sunriseDateTime.time}"
                )
                val sunsetDateTime = (weather.current.sunset * 1000L).asDateTime()
                Text(
                    text = "${stringResource(id = R.string.sunset)} ${sunsetDateTime.time}"
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SwitchElement(
                name = stringResource(id = R.string.daily_forecast),
                isChecked = dailyForecastOn,
                onCheckedChange = { dailyForecastOn = !dailyForecastOn }
            )
            LazyRow(modifier = Modifier.background(AppTheme.colors.secondary)) {
                if (dailyForecastOn) {
                    dailyItems(
                        dailyList = weather.daily,
                        degreesName = degreesName
                    )
                } else {
                    hourlyItems(
                        hourlyList = weather.hourly,
                        degreesName = degreesName
                    )
                }
            }
        }
    }
}

fun LazyListScope.hourlyItems(
    hourlyList: List<Weather.Hourly>,
    degreesName: String
) {
    items(
        items = hourlyList.takeIf { it.size >= 14 }?.slice(1..13) ?: emptyList()
    ) { hourly: Weather.Hourly ->
        Column(
            modifier = Modifier.padding(DIMEN_SMALL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val hourlyDateTime = (hourly.dt * 1000L).asDateTime()
            Text(text = hourlyDateTime.time)
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
                    Text(text = "${hourly.temp}$degreesName")
                    Text(
                        text = "${hourly.wind_speed} ${stringResource(R.string.meters_per_second)} ${
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
                        }"
                    )
                }
            }
        }
    }
}

fun LazyListScope.dailyItems(
    dailyList: List<Weather.Daily>,
    degreesName: String
) {
    itemsIndexed(dailyList) { index: Int, daily: Weather.Daily ->
        Column(
            modifier = Modifier.padding(DIMEN_SMALL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dailyDateTime = (daily.dt * 1000L).asDateTime()
            val time = when (index) {
                0 -> stringResource(R.string.today)
                1 -> stringResource(R.string.tomorrow)
                else -> dailyDateTime.dayOfWeek
            }
            Text(text = time)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DIMEN_SMALL),
                elevation = DIMEN_SMALL,
                shape = Shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(DIMEN_SMALL),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherIconElement(
                        iconCode = daily.weather[0].icon,
                        iconDescription = daily.weather[0].description,
                        iconSize = DIMEN_MEDIUM
                    )
                    Spacer(modifier = Modifier.height(DIMEN_SMALL))
                    Text(text = "${daily.temp.max}$degreesName")
                    Text(text = "${daily.temp.min}$degreesName")
                    Text(
                        text = "${
                            daily.wind_speed
                        } ${
                            stringResource(id = R.string.meters_per_second)
                        } ${
                            with(daily.wind_deg) {
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
                        }"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreenContent(
        city = City("Preview city"),
        weather = Weather()
    )
}
