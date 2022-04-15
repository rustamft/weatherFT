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
import androidx.compose.material.ScaffoldState
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
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.ui.elements.IconButtonElement
import com.rustamft.weatherft.ui.elements.TextButtonElement
import com.rustamft.weatherft.ui.elements.TextFieldElement
import com.rustamft.weatherft.ui.screens.destinations.WeatherScreenDestination
import com.rustamft.weatherft.ui.theme.DIMEN_MEDIUM
import com.rustamft.weatherft.util.ROUTE_LOGIN
import com.rustamft.weatherft.util.appLanguage
import kotlinx.coroutines.flow.first

@Destination(route = ROUTE_LOGIN)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
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
                onClick = {
                    viewModel.updateListOfCities(scaffoldState, cityName, apiKey)
                },
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.find_city)
            )
        }
    }

    Column( // Contains all elements of the screen.
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ApiElementsSet()
        if (apiKey.isNotEmpty()) {
            Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
            CityElementsSet()
        }
        Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
        LazyColumn {
            itemsIndexed(viewModel.listOfCities) { _: Int, city: City ->
                TextButtonElement(
                    onClick = {
                        viewModel.setCity(city)
                        viewModel.setApiKey(apiKey)
                        navigator.navigate(WeatherScreenDestination)
                    },
                    text = "${city.getLocalName(appLanguage)}, ${city.state}, ${city.country}"
                )
            }
        }
    }
}
