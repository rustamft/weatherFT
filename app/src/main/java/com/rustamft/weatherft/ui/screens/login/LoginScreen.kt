package com.rustamft.weatherft.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.ui.IconButtonElement
import com.rustamft.weatherft.ui.TextFieldElement
import com.rustamft.weatherft.ui.screens.destinations.WeatherScreenDestination

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {

    //val weatherPrefs = viewModel.prefsFlow.collectAsState(initial = WeatherPrefs()).value

    @Composable
    fun ApiElementsSet() {

        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextFieldElement(
                text = text,
                onValueChange = { text = it },
                label = stringResource(R.string.open_weather_api_key)
            )
            IconButtonElement(
                onClick = { viewModel.setApiKey(text) },
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = stringResource(R.string.save_api_key)
            )
        }
    }

    @Composable
    fun CityElementsSet() {

        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextFieldElement(
                text = text,
                onValueChange = { text = it },
                label = stringResource(R.string.city)
            )
            IconButtonElement(
                onClick = { viewModel.updateCitiesList(text) }, // TODO: crash
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.find_city)
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        TextButton(
            onClick = {
                viewModel.clearApiKey()
            },
            shape = CircleShape
        ) {
            Text(text = stringResource(R.string.change_api_key))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.apiKeyIsNotSet) {
            ApiElementsSet()
        } else {
            CityElementsSet()
        }
        Spacer(modifier = Modifier.width(9.dp))
        LazyColumn {
            itemsIndexed(viewModel.listOfCities) { index: Int, item: City ->
                Button(
                    onClick = {
                        viewModel.setCity(item)
                        navigator.navigate(WeatherScreenDestination)
                    }
                ) {
                    Text(text = "$item.name $item.state $item.country")
                }
            }
        }
    }
}
