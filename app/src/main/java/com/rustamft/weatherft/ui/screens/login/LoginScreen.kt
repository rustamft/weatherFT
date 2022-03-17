package com.rustamft.weatherft.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rustamft.weatherft.ui.destinations.WeatherScreenDestination

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {

    val viewModel = viewModel()

    var text by remember {
        mutableStateOf("")
    }
    Column {
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
        Button(onClick = {
            navigator.navigate(
                WeatherScreenDestination()
            )
        }) {
            Text(text = "Proceed")
        }
    }

}