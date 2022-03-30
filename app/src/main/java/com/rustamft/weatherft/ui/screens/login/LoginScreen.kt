package com.rustamft.weatherft.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.rustamft.weatherft.app.appLanguage
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.ui.IconButtonElement
import com.rustamft.weatherft.ui.TextButtonElement
import com.rustamft.weatherft.ui.TextFieldElement
import com.rustamft.weatherft.ui.screens.destinations.WeatherScreenDestination
import kotlinx.coroutines.flow.first

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    var apiKey by remember {
        mutableStateOf("")
    }
    var cityName by remember {
        mutableStateOf("")
    }

    LaunchedEffect(scope) {
        val weatherPrefs = viewModel.prefsFlow.first()
        apiKey = weatherPrefs.apiKey
        cityName = weatherPrefs.city.getLocalName(appLanguage)
    }

    @Composable
    fun ApiElementsSet() {
        // TODO: rearrange this
        Row(
            modifier = Modifier.width(365.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextFieldElement(
                text = apiKey,
                onValueChange = { apiKey = it },
                label = stringResource(R.string.open_weather_api_key)
            )
        }
    }

    @Composable
    fun CityElementsSet() {

        Row(
            modifier = Modifier.width(365.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextFieldElement(
                text = cityName,
                onValueChange = { cityName = it },
                label = stringResource(R.string.city)
            )
            IconButtonElement(
                onClick = { viewModel.updateListOfCities(cityName) },
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.find_city)
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ApiElementsSet()
        if (apiKey.isNotEmpty()) {
            Spacer(modifier = Modifier.height(9.dp))
            CityElementsSet()
        }
        Spacer(modifier = Modifier.height(9.dp))
        LazyColumn {
            itemsIndexed(viewModel.listOfCities) { index: Int, item: City ->
                TextButtonElement(
                    onClick = {
                        viewModel.setApiKey(apiKey)
                        viewModel.setCity(item)
                        navigator.navigate(WeatherScreenDestination)
                    },
                    text = "${item.getLocalName(appLanguage)}, ${item.state}, ${item.country}"
                )
            }
        }
    }
}
