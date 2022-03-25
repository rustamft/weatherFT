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
import com.rustamft.weatherft.ui.activity.IconButtonElement
import com.rustamft.weatherft.ui.screens.destinations.LoginScreenDestination
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
                        text = viewModel.city.getLocalName(Locale.getDefault().language),
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    IconButtonElement(
                        onClick = {
                            viewModel.clearCity()
                            navigator.navigate(LoginScreenDestination)
                        },
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = stringResource(R.string.change_city_settings)
                    )
                }
                Text(
                    text = stringResource(R.string.updated_at) + viewModel.lastTimeUpdatedString,
                    modifier = Modifier.offset(0.dp, 50.dp)
                )
            }
            Box(contentAlignment = Alignment.TopCenter) {
                Text(
                    text = viewModel.currentWeather.current.temp.toString() +
                            stringResource(R.string.degrees_centigrade),
                    fontSize = 50.sp
                )
                Text(
                    text = stringResource(R.string.feels_like) +
                            viewModel.currentWeather.current.feels_like +
                            stringResource(R.string.degrees_centigrade),
                    modifier = Modifier.offset(0.dp, 60.dp)
                )
            }
            Text(
                text = viewModel.currentWeather.current.weather[0].description,
                fontSize = 30.sp
            )
        }
    }
}
