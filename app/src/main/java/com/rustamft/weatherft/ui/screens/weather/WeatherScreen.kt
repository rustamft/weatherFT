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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.appLanguage
import com.rustamft.weatherft.database.datastore.WeatherPrefs
import com.rustamft.weatherft.ui.IconButtonElement
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination

@Destination(start = true)
@Composable
fun WeatherScreen(
    navigator: DestinationsNavigator,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val weatherPrefs = viewModel.prefsFlow.collectAsState(initial = WeatherPrefs()).value

    viewModel.updateData()
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
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(9.dp))
                IconButtonElement(
                    onClick = {
                        navigator.navigate(LoginScreenDestination)
                    },
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.change_city_settings)
                )
            }
            Text(
                text = stringResource(R.string.updated_at) + weatherPrefs.lastTimeUpdatedString,
                modifier = Modifier.offset(0.dp, 50.dp)
            )
        }
        Box(contentAlignment = Alignment.TopCenter) {
            Text(
                text = weatherPrefs.currentWeather.current.temp.toString() +
                        stringResource(R.string.degrees_centigrade),
                fontSize = 50.sp
            )
            Text(
                text = stringResource(R.string.feels_like) +
                        weatherPrefs.currentWeather.current.feels_like +
                        stringResource(R.string.degrees_centigrade),
                modifier = Modifier.offset(0.dp, 60.dp)
            )
        }
        Text(
            text = weatherPrefs.currentWeather.current.weather[0].description,
            fontSize = 30.sp
        )
    }
}
