package com.rustamft.weatherft.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = viewModel()
) {

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
            viewModel.findCity(text)
        }) {
            Text(text = "Find")
        }
    }
    /*
    navigator.navigate(
                WeatherScreenDestination()
            )
     */
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //LoginScreen()
}