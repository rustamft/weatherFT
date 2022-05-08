package com.rustamft.weatherft.presentation.screen.login

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
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.R
import com.rustamft.weatherft.app.App
import com.rustamft.weatherft.domain.model.AppPreferences
import com.rustamft.weatherft.domain.model.City
import com.rustamft.weatherft.domain.util.ROUTE_LOGIN
import com.rustamft.weatherft.presentation.element.IconButtonElement
import com.rustamft.weatherft.presentation.element.SwitchElement
import com.rustamft.weatherft.presentation.element.TextButtonElement
import com.rustamft.weatherft.presentation.element.TextFieldElement
import com.rustamft.weatherft.presentation.screen.destinations.WeatherScreenDestination
import com.rustamft.weatherft.presentation.theme.DIMEN_MEDIUM

@Destination(route = ROUTE_LOGIN)
@Composable
internal fun LoginScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState, // From DependenciesContainer.
    appPreferencesState: State<AppPreferences>, // From DependenciesContainer.
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    @Composable
    fun PreferencesElementsSet() {
        SwitchElement(
            name = stringResource(R.string.dark_theme),
            isChecked = appPreferencesState.value.darkTheme,
            onCheckedChange = {
                loginViewModel.saveAppPreferences(
                    appPreferences = AppPreferences(darkTheme = it)
                )
            }
        )
    }

    @Composable
    fun ApiElementsSet() {
        Row(
            modifier = Modifier.width(365.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextFieldElement(
                text = loginViewModel.apiKey,
                onValueChange = { loginViewModel.apiKey = it },
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
                text = loginViewModel.cityName,
                onValueChange = { loginViewModel.cityName = it },
                label = stringResource(R.string.city)
            )
            IconButtonElement(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.find_city),
                onClick = {
                    loginViewModel.updateListOfCities(scaffoldState)
                }
            )
        }
    }

    @Composable
    fun ListOfCitiesElementsSet() {
        LazyColumn {
            itemsIndexed(loginViewModel.listOfCities) { _: Int, city: City ->
                TextButtonElement(
                    onClick = {
                        loginViewModel.saveCity(city)
                        loginViewModel.saveApiKey()
                        navigator.navigate(WeatherScreenDestination)
                    },
                    text = "${city.localNames[App.language]}, ${city.state}, ${city.country}"
                )
            }
        }
    }

    Column( // Contains all elements of the screen.
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreferencesElementsSet()
        Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
        ApiElementsSet()
        if (loginViewModel.apiKey.isNotEmpty()) {
            Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
            CityElementsSet()
        }
        Spacer(modifier = Modifier.height(DIMEN_MEDIUM))
        ListOfCitiesElementsSet()
    }
}
