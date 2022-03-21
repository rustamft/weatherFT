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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.database.entity.City
import com.rustamft.weatherft.ui.screens.destinations.WeatherScreenDestination

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (viewModel.isApiKeyEntered) {
            CityElementsSet(viewModel)
        } else {
            ApiElementsSet(viewModel)
        }

        LazyColumn {
            itemsIndexed(viewModel.listOfCities) { index: Int, item: City ->
                Button(onClick = {
                    viewModel.setCity(item)
                    navigator.navigate(WeatherScreenDestination)
                }
                ) {
                    Text(text = item.name)
                }
            }
        }
    }
}

@Composable
private fun CityElementsSet(viewModel: LoginViewModel) {

    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = {
            Text("City")
        }
    )

    Spacer(modifier = Modifier.height(9.dp))

    Row {
        Button(onClick = {
            viewModel.setApiKey("")
        }) {
            Text(text = "Change API key")
        }
        
        Spacer(modifier = Modifier.width(9.dp))
        
        Button(onClick = {
            viewModel.updateCitiesList(text)
        }) {
            Text(text = "Find")
        }
    }
}

@Composable
private fun ApiElementsSet(viewModel: LoginViewModel) {

    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = {
            Text("Open Weather API key")
        }
    )

    Spacer(modifier = Modifier.height(9.dp))

    Button(onClick = {
        viewModel.setApiKey(text)
    }) {
        Text(text = "Save")
    }
}