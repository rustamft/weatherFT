package com.rustamft.weatherft.ui.screens.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.rustamft.weatherft.R
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.database.entity.Weather.Daily
import com.rustamft.weatherft.ui.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.ui.theme.FONT_SIZE_SMALL
import com.rustamft.weatherft.util.PATTERN_DATE
import com.rustamft.weatherft.util.ROUTE_FORECAST
import com.rustamft.weatherft.util.TimeProvider

@Destination(route = ROUTE_FORECAST)
@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = hiltViewModel()
) {

    val weatherPrefs = viewModel.prefsFlow.collectAsState(initial = WeatherPrefs()).value

    LazyColumn(
        modifier = Modifier.padding(DIMEN_MEDIUM),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(weatherPrefs.weather.daily) { index: Int, daily: Daily ->

            val time = when (index) {
                0 -> stringResource(R.string.today)
                1 -> stringResource(R.string.tomorrow)
                else -> TimeProvider.millisToString(daily.dt * 1000L, PATTERN_DATE)
            }
            val degrees = stringResource(R.string.degrees_centigrade)

            Text(text = time, fontSize = FONT_SIZE_SMALL)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DIMEN_MEDIUM)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column {
                            Text(text = "${daily.temp.max}$degrees (${daily.temp.min}$degrees)")
                        }
                        Text(text = daily.weather[0].description)
                    }
                }
            }
            Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
        }
    }
}
