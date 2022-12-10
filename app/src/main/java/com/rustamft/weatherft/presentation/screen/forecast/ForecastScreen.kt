package com.rustamft.weatherft.presentation.screen.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.rustamft.weatherft.R
import com.rustamft.weatherft.domain.model.Weather
import com.rustamft.weatherft.domain.util.ROUTE_FORECAST
import com.rustamft.weatherft.domain.util.asDateTime
import com.rustamft.weatherft.presentation.element.WeatherIconElement
import com.rustamft.weatherft.presentation.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.presentation.theme.DIMEN_SMALL
import com.rustamft.weatherft.presentation.theme.FONT_SIZE_SMALL
import com.rustamft.weatherft.presentation.theme.Shapes
import kotlin.math.roundToInt

@Destination(route = ROUTE_FORECAST)
@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = hiltViewModel(),
    paddingValues: PaddingValues, // From DependenciesContainer.
    weatherState: State<Weather> = viewModel.weatherFlow.collectAsState(initial = Weather())
) {

    val weather by weatherState
    val degreesName = stringResource(id = R.string.degrees_centigrade)
    val speedUnitsName = stringResource(id = R.string.meters_per_second)

    ForecastScreenContent(
        weather = weather,
        degreesName = degreesName,
        speedUnitsName = speedUnitsName,
        bottomPadding = paddingValues.calculateBottomPadding()
    )
}

@Composable
fun ForecastScreenContent(
    weather: Weather,
    degreesName: String,
    speedUnitsName: String,
    bottomPadding: Dp
) {

    LazyColumn(
        modifier = Modifier
            .padding(bottom = bottomPadding)
            .padding(horizontal = DIMEN_SMALL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(weather.daily) { index: Int, daily: Weather.Daily ->

            val dailyDateTime = (daily.dt * 1000L).asDateTime()
            val time = when (index) {
                0 -> "${stringResource(R.string.today)} (${dailyDateTime.dayOfWeek})"
                1 -> "${stringResource(R.string.tomorrow)} (${dailyDateTime.dayOfWeek})"
                else -> "${dailyDateTime.date} (${dailyDateTime.dayOfWeek})"
            }

            Text(text = time, fontSize = FONT_SIZE_SMALL)

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${stringResource(id = R.string.temperature_max)} ${
                                daily.temp.max
                                }$degreesName"
                            )
                            Text(
                                text = "${stringResource(id = R.string.temperature_min)} ${
                                daily.temp.min
                                }$degreesName"
                            )
                            Text(
                                text = "${stringResource(id = R.string.wind)} ${
                                daily.wind_speed
                                } $speedUnitsName ${
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
                        WeatherIconElement(
                            iconCode = daily.weather[0].icon,
                            iconDescription = daily.weather[0].description,
                            iconSize = DIMEN_MEDIUM
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(DIMEN_SMALL))
        }
    }
}

@Preview
@Composable
fun ForecastScreenPreview() {
    val daily = Weather.Daily()
    ForecastScreenContent(
        weather = Weather(
            daily = listOf(daily, daily, daily, daily, daily, daily)
        ),
        degreesName = stringResource(id = R.string.degrees_centigrade),
        speedUnitsName = stringResource(id = R.string.meters_per_second),
        bottomPadding = 5.dp
    )
}
