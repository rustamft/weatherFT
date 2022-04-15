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
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.ui.activity.OnLifecycleEvent
import com.rustamft.weatherft.ui.elements.IconButtonElement
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination
import com.rustamft.weatherft.ui.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.ui.theme.FONT_SIZE_BIG
import com.rustamft.weatherft.ui.theme.FONT_SIZE_NORMAL
import com.rustamft.weatherft.util.PATTERN_DATE_TIME
import com.rustamft.weatherft.util.ROUTE_WEATHER
import com.rustamft.weatherft.util.TimeProvider
import com.rustamft.weatherft.util.appLanguage

@Destination(start = true, route = ROUTE_WEATHER)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {

    val weatherPrefs = viewModel.prefsFlow.collectAsState(initial = WeatherPrefs()).value

    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.updateData(scaffoldState)
        }
    }
    if (viewModel.prefsAreEmpty) {
        navigator.navigate(LoginScreenDestination)
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
                    text = weatherPrefs.city.getLocalName(appLanguage),
                    fontSize = FONT_SIZE_NORMAL
                )
                Spacer(modifier = Modifier.width(DIMEN_MEDIUM))
                IconButtonElement(
                    onClick = {
                        navigator.navigate(LoginScreenDestination)
                    },
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.change_city_settings)
                )
            }
            Text(
                text = stringResource(R.string.updated_at) +
                        TimeProvider.millisToString(
                            weatherPrefs.lastTimeUpdated,
                            PATTERN_DATE_TIME
                        ),
                modifier = Modifier.offset(0.dp, 50.dp)
            )
        }
        Box(contentAlignment = Alignment.TopCenter) {
            Text(
                text = weatherPrefs.weather.current.temp.toString() +
                        stringResource(R.string.degrees_centigrade),
                fontSize = FONT_SIZE_BIG
            )
            Text(
                text = stringResource(R.string.feels_like) +
                        weatherPrefs.weather.current.feels_like +
                        stringResource(R.string.degrees_centigrade),
                modifier = Modifier.offset(0.dp, 60.dp)
            )
        }
        Text(
            text = weatherPrefs.weather.current.weather[0].description,
            fontSize = FONT_SIZE_NORMAL
        )
        Text(
            text = stringResource(R.string.wind) +
                    weatherPrefs.weather.current.wind_speed +
                    stringResource(R.string.meters_per_second)
        )
    }
}
