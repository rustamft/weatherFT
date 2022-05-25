package com.rustamft.weatherft.presentation.screen.login

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
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
import com.rustamft.weatherft.domain.util.API_KEYS_LINK
import com.rustamft.weatherft.domain.util.ROUTE_LOGIN
import com.rustamft.weatherft.presentation.element.IconButtonElement
import com.rustamft.weatherft.presentation.element.SwitchElement
import com.rustamft.weatherft.presentation.element.TextButtonElement
import com.rustamft.weatherft.presentation.element.TextFieldElement
import com.rustamft.weatherft.presentation.screen.destinations.WeatherScreenDestination
import com.rustamft.weatherft.presentation.theme.DIMEN_SMALL

@Destination(route = ROUTE_LOGIN)
@Composable
internal fun LoginScreen(
    navigator: DestinationsNavigator,
    scaffoldState: ScaffoldState, // From DependenciesContainer.
    appPreferencesState: State<AppPreferences>, // From DependenciesContainer.
    viewModel: LoginViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.errorFlow.collect { error ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = error
            )
        }
    }

    @Composable
    fun PreferencesElementsSet() {
        SwitchElement(
            name = stringResource(R.string.dark_theme),
            isChecked = appPreferencesState.value.darkTheme,
            onCheckedChange = {
                viewModel.saveAppPreferences(
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
            var openDialog by remember { mutableStateOf(false) }
            var openApiKeysPage by remember { mutableStateOf(false) }
            TextFieldElement(
                text = viewModel.apiKey,
                onValueChange = { viewModel.apiKey = it },
                label = stringResource(R.string.open_weather_api_key)
            )
            IconButtonElement(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(R.string.open_weather_api_info)
            ) {
                openDialog = true
            }
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = { openDialog = false },
                    title = { Text(text = stringResource(id = R.string.app_info)) },
                    text = {
                        Text(text = stringResource(id = R.string.open_weather_api_info_content))
                    },
                    confirmButton = {
                        TextButtonElement(
                            onClick = { openDialog = false },
                            text = stringResource(R.string.action_close)
                        )
                    },
                    dismissButton = {
                        TextButtonElement(
                            onClick = { openApiKeysPage = true },
                            text = stringResource(id = R.string.open_weather_map_site)
                        )
                    },
                    backgroundColor = MaterialTheme.colors.background
                )
            }
            if (openApiKeysPage) {
                val uriHandler = LocalUriHandler.current
                val uri = Uri.parse(API_KEYS_LINK)
                uriHandler.openUri(uri.toString())
                openApiKeysPage = false
            }
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
                text = viewModel.cityName,
                onValueChange = { viewModel.cityName = it },
                label = stringResource(R.string.city)
            )
            IconButtonElement(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.find_city),
                onClick = {
                    viewModel.updateListOfCities()
                }
            )
        }
    }

    @Composable
    fun ListOfCitiesElementsSet() {
        LazyColumn {
            itemsIndexed(viewModel.listOfCities) { _: Int, city: City ->
                TextButtonElement(
                    onClick = {
                        viewModel.saveCity(city)
                        viewModel.saveApiKey()
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
        Spacer(modifier = Modifier.height(DIMEN_SMALL))
        ApiElementsSet()
        if (viewModel.apiKey.isNotEmpty()) {
            Spacer(modifier = Modifier.height(DIMEN_SMALL))
            CityElementsSet()
        }
        Spacer(modifier = Modifier.height(DIMEN_SMALL))
        ListOfCitiesElementsSet()
    }
}
